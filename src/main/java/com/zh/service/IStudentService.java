package com.zh.service;

import com.zh.domain.Student;

import java.util.List;

public interface IStudentService {

    Student findStudent(String id);

    List<Student> findAllStudentByList();

    boolean insertStudent(Student student);

    boolean updateStudent(Student student);

    boolean deleteStudent(Student student);
}
