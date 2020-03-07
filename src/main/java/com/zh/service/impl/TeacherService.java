package com.zh.service.impl;

import com.zh.dao.ITeacherDao;
import com.zh.domain.Teacher;
import com.zh.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherService implements ITeacherService {
    @Autowired
    private ITeacherDao teacherDao;

    @Override
    public List<Teacher> findAllTeacherList() {
        return teacherDao.findAllTeacherList();
    }

    @Override
    public Teacher findTeacher(String id) {
        return teacherDao.findTeacher(id);
    }

    @Override
    public boolean insertTeacher(Teacher teacher) {
        return teacherDao.insertTeacher(teacher);
    }

    @Override
    public boolean deleteTeacher(String id) {
        return teacherDao.deleteTeacher(id);
    }
}
