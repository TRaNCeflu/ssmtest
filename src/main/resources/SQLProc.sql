
-- -2 ִ��ʧ�� -1 unionʧ�� 0 exceptʧ�� 1 �ɹ�
if (exists (select * from sys.objects where name = 'submitJudgeForSelect')) drop proc submitJudgeForSelect   --�жϴ洢�����Ƿ���ڣ�������ɾ��Ȼ���ؽ���
go
create proc submitJudgeForSelect
 @sqlStudent nvarchar(1000),
 @sqlTeacher nvarchar(1000),
 @result int output -- -3 ��ʦsqlִ��ʧ�� -2 ѧ��sqlִ��ʧ�� -1 �����ͬ 0 �����ͬ 1 �ɹ�
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

	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('��ʦsqlִ��ʧ��')
		set @result = -3
		return @result
	end catch

	set transaction isolation level serializable
	BEGIN TRANSACTION

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
		commit transaction
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
	commit transaction
	return @result
end

-- ΪAlter�����������ж�sql����Ƿ�������
if (exists (select * from sys.objects where name = 'submitJudgeForAlter')) drop proc submitJudgeForAlter   --�жϴ洢�����Ƿ���ڣ�������ɾ��Ȼ���ؽ���
go
create proc submitJudgeForAlter
 @sqlTeacher nvarchar(1000),
 @sqlStudent nvarchar(1000),
 @tableName nvarchar(50),
 @result int output --  -3 ��ʦsqlִ��ʧ�� -2 sql���ִ��ʧ�� -1 �����ڴ˱� 0 �����ͬ 1 ִ�гɹ� 
 as
begin
	declare @initTmpTableForStudent nvarchar(200)
	declare @initTmpTableForTeacher nvarchar(200)
	set @initTmpTableForStudent = 'select * into ##atmp1 from '+@tableName
	set @initTmpTableForTeacher = 'select * into ##atmp2 from '+@tableName
	--�Ƿ�����������������Ƶ���ʱ��##atmp1,##atmp2
	set transaction isolation level serializable
	BEGIN TRANSACTION
	begin try
		exec(@initTmpTableForStudent)
	end try
	begin catch
		print('�����ڴ˱�')
		set @result = -1
		commit transaction
		return @result
	end catch
	begin try
		exec(@initTmpTableForTeacher)
	end try
	begin catch
		print('�����ڴ˱�')
		set @result = -1
		commit transaction
		return @result
	end catch

	--����ʱ�� atmp1 ��ִ��studentsql��ʧ�ܷ��� -2
	begin try
		exec(@sqlStudent)
	end try
	begin catch
		print('ѧ��sql���ִ��ʧ��')
		set @result = -2
		drop table ##atmp1
		drop table ##atmp2
		commit transaction
		return @result
	end catch

	--����ʱ�� atmp2 ��ִ��teachersql��ʧ�ܷ��� -3
	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('��ʦsql���ִ��ʧ��')
		set @result = -3
		drop table ##atmp1
		drop table ##atmp2
		commit transaction
		return @result
	end catch

	--���������������ʱ����ִ����ϣ�ִ��except����
	declare @sqlExcept1 int
	set @sqlExcept1 = (select count(*) from (select * from ##atmp1 except select * from ##atmp2)c)
	declare @sqlExcept2 int
	set @sqlExcept2 = (select count(*) from (select * from ##atmp2 except select * from ##atmp1)d)
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

	drop table ##atmp1
	drop table ##atmp2
	commit transaction
	return @result
	
end



declare @temp nvarchar(1000)
declare @tempTeacher nvarchar(1000)
declare @re int
set @temp = 'select * from question afsfaesfawefw'
set @tempTeacher = 'select * from question'
exec submitJudgeForSelect @temp,@tempTeacher,@re;
print @re

declare @





