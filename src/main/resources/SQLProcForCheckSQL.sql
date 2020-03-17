-- 为Select操作所做的判断sql语句是否能运行
if (exists (select * from sys.objects where name = 'judgeSQLRightForSelect')) drop proc judgeSQLRightForSelect   --判断存储过程是否存在，存在则删除然后重建。
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
		print('sql执行失败')
		set @result = 0
		return @result
	end catch

	set @result = 1
	return @result
	
end


-- 为Alter操作所做的判断sql语句是否能运行
if (exists (select * from sys.objects where name = 'judgeSQLRightForAlter')) drop proc judgeSQLRightForAlter   --判断存储过程是否存在，存在则删除然后重建。
go
create proc judgeSQLRightForAlter
 @sqlTeacher nvarchar(1000),
 @tableName nvarchar(50),
 @result int output -- -1 不存在此表 0 sql语句执行失败 1 执行成功
 as
begin
	declare @initTmpTable nvarchar(200)
	--设置事务隔离级别为serializable
	set transaction isolation level serializable
	BEGIN TRANSACTION
	set @initTmpTable = 'select * into ##jtmp from '+@tableName
	--是否存在这个表，将这个表复制到临时表##jtmp
	begin try
		exec(@initTmpTable)
	end try
	begin catch
		print('不存在此表')
		set @result = -1
		commit transaction
		return @result
	end catch
	--在临时表jtmp中执行sql命令，找到被影响的行数@@rowcount
	
	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('sql语句执行失败')
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

