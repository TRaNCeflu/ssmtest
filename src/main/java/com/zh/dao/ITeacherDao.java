package com.zh.dao;

import com.zh.domain.Teacher;

import java.util.List;

public interface ITeacherDao {
    List<Teacher> findAllTeacherList();
    Teacher findTeacher(String id);
    boolean updateTeacherPassword(Teacher teacher);
    boolean insertTeacher(Teacher teacher);
    boolean deleteTeacher(String id);
}
