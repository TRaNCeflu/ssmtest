package com.zh.dao;

import com.zh.domain.Student;

import java.util.List;

public interface IStudentDao {
    Student findStudent(String id);
    List<Student> findAllStudentByList();
    boolean insertStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(Student student);
    boolean updateStudentPassword(Student student);
}
