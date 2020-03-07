package com.zh.service;

import com.zh.domain.Teacher;

import java.util.List;

public interface ITeacherService {
    List<Teacher> findAllTeacherList();
    Teacher findTeacher(String id);
    boolean insertTeacher(Teacher teacher);
    boolean deleteTeacher(String id);
}
