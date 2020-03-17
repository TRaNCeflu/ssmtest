-- ΪSelect�����������ж�sql����Ƿ�������
if (exists (select * from sys.objects where name = 'judgeSQLRightForSelect')) drop proc judgeSQLRightForSelect   --�жϴ洢�����Ƿ���ڣ�������ɾ��Ȼ���ؽ���
go
create proc judgeSQLRightForSelect
 @sqlTeacher nvarchar(1000),
 @result int output
 as
begin
	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('sqlִ��ʧ��')
		set @result = 0
		return @result
	end catch

	set @result = 1
	return @result
	
end


-- ΪAlter�����������ж�sql����Ƿ�������
if (exists (select * from sys.objects where name = 'judgeSQLRightForAlter')) drop proc judgeSQLRightForAlter   --�жϴ洢�����Ƿ���ڣ�������ɾ��Ȼ���ؽ���
go
create proc judgeSQLRightForAlter
 @sqlTeacher nvarchar(1000),
 @tableName nvarchar(50),
 @result int output -- -1 �����ڴ˱� 0 sql���ִ��ʧ�� 1 ִ�гɹ�
 as
begin
	declare @initTmpTable nvarchar(200)
	--����������뼶��Ϊserializable
	set transaction isolation level serializable
	BEGIN TRANSACTION
	set @initTmpTable = 'select * into ##jtmp from '+@tableName
	--�Ƿ�����������������Ƶ���ʱ��##jtmp
	begin try
		exec(@initTmpTable)
	end try
	begin catch
		print('�����ڴ˱�')
		set @result = -1
		commit transaction
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
		commit transaction
		return @result
	end catch

	set @result = 1
	drop table ##jtmp
	commit transaction
	return @result
	
	
end



declare @temp nvarchar(1000)
declare @tempTeacher nvarchar(1000)
declare @tmpTableName nvarchar(50)
declare @re int
set @temp = 'update ##jtmp set question_content = ''sf'' where question_type = 1'
set @tempTeacher = 'select * from question'
set @tmpTableName = 'question'
exec judgeSQLRightForAlter @temp,@tmpTableName,@re;
print @re

