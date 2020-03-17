
-- -2 执行失败 -1 union失败 0 except失败 1 成功
if (exists (select * from sys.objects where name = 'submitJudgeForSelect')) drop proc submitJudgeForSelect   --判断存储过程是否存在，存在则删除然后重建。
go
create proc submitJudgeForSelect
 @sqlStudent nvarchar(1000),
 @sqlTeacher nvarchar(1000),
 @result int output -- -3 老师sql执行失败 -2 学生sql执行失败 -1 结果不同 0 结果不同 1 成功
as
begin
	begin try
		exec(@sqlStudent)
	end try
	begin catch
		print('学生sql执行失败')
		set @result = -2
		return @result
	end catch

	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('老师sql执行失败')
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
		print ('Union失败')
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
		print('except成功')
		set @result = 1
	end
	else
	begin
		print('except失败')
		set @result = 0
	end
	drop table ##stmp2
	drop table ##stmp1
	commit transaction
	return @result
end

-- 为Alter操作所做的判断sql语句是否能运行
if (exists (select * from sys.objects where name = 'submitJudgeForAlter')) drop proc submitJudgeForAlter   --判断存储过程是否存在，存在则删除然后重建。
go
create proc submitJudgeForAlter
 @sqlTeacher nvarchar(1000),
 @sqlStudent nvarchar(1000),
 @tableName nvarchar(50),
 @result int output --  -3 老师sql执行失败 -2 sql语句执行失败 -1 不存在此表 0 结果不同 1 执行成功 
 as
begin
	declare @initTmpTableForStudent nvarchar(200)
	declare @initTmpTableForTeacher nvarchar(200)
	set @initTmpTableForStudent = 'select * into ##atmp1 from '+@tableName
	set @initTmpTableForTeacher = 'select * into ##atmp2 from '+@tableName
	--是否存在这个表，将这个表复制到临时表##atmp1,##atmp2
	set transaction isolation level serializable
	BEGIN TRANSACTION
	begin try
		exec(@initTmpTableForStudent)
	end try
	begin catch
		print('不存在此表')
		set @result = -1
		commit transaction
		return @result
	end catch
	begin try
		exec(@initTmpTableForTeacher)
	end try
	begin catch
		print('不存在此表')
		set @result = -1
		commit transaction
		return @result
	end catch

	--在临时表 atmp1 中执行studentsql，失败返回 -2
	begin try
		exec(@sqlStudent)
	end try
	begin catch
		print('学生sql语句执行失败')
		set @result = -2
		drop table ##atmp1
		drop table ##atmp2
		commit transaction
		return @result
	end catch

	--在临时表 atmp2 中执行teachersql，失败返回 -3
	begin try
		exec(@sqlTeacher)
	end try
	begin catch
		print('老师sql语句执行失败')
		set @result = -3
		drop table ##atmp1
		drop table ##atmp2
		commit transaction
		return @result
	end catch

	--两条语句在两个临时表中执行完毕，执行except操作
	declare @sqlExcept1 int
	set @sqlExcept1 = (select count(*) from (select * from ##atmp1 except select * from ##atmp2)c)
	declare @sqlExcept2 int
	set @sqlExcept2 = (select count(*) from (select * from ##atmp2 except select * from ##atmp1)d)
	if (@sqlExcept1 = 0 and @sqlExcept2 = 0)
	begin
		print('except成功')
		set @result = 1
	end
	else
	begin
		print('except失败')
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





