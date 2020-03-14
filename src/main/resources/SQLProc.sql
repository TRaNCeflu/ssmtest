
-- -2 ִ��ʧ�� -1 unionʧ�� 0 exceptʧ�� 1 �ɹ�
if (exists (select * from sys.objects where name = 'submitJudgeForSelect')) drop proc submitJudgeForSelect   --�жϴ洢�����Ƿ���ڣ�������ɾ��Ȼ���ؽ���
go
create proc submitJudgeForSelect
 @sqlStudent nvarchar(1000),
 @sqlTeacher nvarchar(1000),
 @result int output
as
begin
	begin try
		exec(@sqlStudent)
	end try
	begin catch
		print('ѧ��sqlִ��ʧ��')
		set @result = -2
		return @result
	end catch

	declare @sqltmp1 nvarchar(1000)
	set @sqltmp1 = 'select * into ##stmp1 from ('+ @sqlStudent+')a'
	exec(@sqltmp1)

	declare @sqltmp2 nvarchar(1000)
	set @sqltmp2 = 'select * into ##stmp2 from ('+ @sqlTeacher+')b'
	exec(@sqltmp2)

	declare @sqlUnion varchar(200)
	set @sqlUnion = 'select * from ##stmp1 union select * from ##stmp2'
	begin try
		exec(@sqlUnion)
	end try
	begin catch
		print ('Unionʧ��')
		drop table ##stmp2
		drop table ##stmp1
		set @result = -1
		return @result
	end catch

	declare @sqlExcept1 int
	set @sqlExcept1 = (select count(*) from (select * from ##stmp1 except select * from ##stmp2)c)
	declare @sqlExcept2 int
	set @sqlExcept2 = (select count(*) from (select * from ##stmp2 except select * from ##stmp1)d)
	if (@sqlExcept1 = 0 and @sqlExcept2 = 0)
	begin
		print('except�ɹ�')
		set @result = 1
	end
	else
	begin
		print('exceptʧ��')
		set @result = 0
	end
	drop table ##stmp2
	drop table ##stmp1
	return @result
end

-- ΪAlter�����������ж�sql����Ƿ�������
if (exists (select * from sys.objects where name = 'submitJudgeForAlter')) drop proc submitJudgeForAlter   --�жϴ洢�����Ƿ���ڣ�������ɾ��Ȼ���ؽ���
go
create proc submitJudgeForAlter
 @sqlTeacher nvarchar(1000),
 @tableName nvarchar(50),
 @result int output -- -1 �����ڴ˱� 0 sql���ִ��ʧ�� 1 ִ�гɹ�
 as
begin
	declare @initTmpTable nvarchar(200)
	set @initTmpTable = 'select * into ##jtmp from '+@tableName
	--�Ƿ�����������������Ƶ���ʱ��##jtmp
	begin try
		exec(@initTmpTable)
	end try
	begin catch
		print('�����ڴ˱�')
		set @result = -1
		return @result
	end catch
	--����ʱ��jtmp��ִ��sql����ҵ���Ӱ�������@@rowcount
	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('sql���ִ��ʧ��')
		set @result = 0
		drop table ##jtmp
		return @result
	end catch

	set @result = 1
	drop table ##jtmp
	return @result
	
end



declare @temp nvarchar(1000)
declare @tempTeacher nvarchar(1000)
declare @re int
set @temp = 'select * from question '
set @tempTeacher = 'select * from question'
exec submitJudgeForSelect @temp,@tempTeacher,@re;
print @re





