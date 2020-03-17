package com.zh.service.impl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.zh.dao.IStudentDao;
import com.zh.domain.Student;
import com.zh.service.IStudentService;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements IStudentService{
    @Resource
    private IStudentDao studentDao;

    public Student findStudent(String id){
        return studentDao.findStudent(id);
    }

    public List<Student> findAllStudentByList(){
        return studentDao.findAllStudentByList();
    }

    public boolean insertStudent(Student student){
        try{
            studentDao.insertStudent(student);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean updateStudent(Student student){
        return studentDao.updateStudent(student);
    }

    public boolean deleteStudent(Student student) {
        return studentDao.deleteStudent(student);
    }

    @Override
    public int findAllStudentCount() {
        return studentDao.findAllStudentCount();
    }

}
