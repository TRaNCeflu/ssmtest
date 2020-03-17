package com.zh.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.zh.domain.Student;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface IStudentDao {
    Student findStudent(String id);
    List<Student> findAllStudentByList();
    int findAllStudentCount();
    boolean insertStudent(Student student) throws SQLServerException;
    boolean updateStudent(Student student);
    boolean deleteStudent(Student student);
    boolean updateStudentPassword(Student student);
}
