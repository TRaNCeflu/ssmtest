package com.zh.service;

import com.zh.dao.IStudentDao;
import com.zh.dao.ITeacherDao;
import com.zh.domain.Student;
import com.zh.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("passwordService")
public class PasswordService {
    @Autowired
    private IStudentDao studentDao;

    @Autowired
    private ITeacherDao teacherDao;

    public boolean changeTeacherPassword(Teacher teacher){
        return  teacherDao.updateTeacherPassword(teacher);
    }

    public boolean changeStudentPassword(Student student){
        return studentDao.updateStudentPassword(student);
    }
}
