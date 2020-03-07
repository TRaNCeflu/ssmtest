package com.zh.service.impl;

import com.zh.dao.IStudentDao;
import com.zh.dao.ITeacherDao;
import com.zh.domain.Student;
import com.zh.domain.Teacher;
import com.zh.service.ILoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
    @Resource
    private IStudentDao studentDao;

    @Resource
    private ITeacherDao teacherDao;

    public Student findStudent(String id){
        return this.studentDao.findStudent(id);
    }

    public Teacher findTeacher(String id){
        return this.teacherDao.findTeacher(id);
    }


}
