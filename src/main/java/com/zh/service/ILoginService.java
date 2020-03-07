package com.zh.service;

import com.zh.domain.Student;
import com.zh.domain.Teacher;

public interface ILoginService {
    Student findStudent(String id);
    Teacher findTeacher(String id);
}
