
-- -2 执行失败 -1 union失败 0 except失败 1 成功
if (exists (select * from sys.objects where name = 'test')) drop proc test   --判断存储过程是否存在，存在则删除然后重建。
go
create proc test
 @sqlStudent nvarchar(1000),
 @sqlTeacher nvarchar(1000),
 @result int output
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
	return @result
	
end



declare @temp nvarchar(1000)
declare @tempTeacher nvarchar(1000)
declare @re int
set @temp = 'select * from question where question_type = 1'
set @tempTeacher = 'select * from question'
exec test @temp,@tempTeacher,@re;
print @re





