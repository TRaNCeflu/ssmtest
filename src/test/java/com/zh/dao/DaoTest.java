package com.zh.dao;

import com.zh.domain.Question;
import com.zh.domain.Student;
import com.zh.domain.Teacher;
import com.zh.service.ILoginService;
import com.zh.service.IQuestionService;
import com.zh.service.IStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class DaoTest {
    @Resource
    private ILoginService loginService;

    @Resource
    private IStudentService studentService;
    @Autowired
    private IQuestionService questionService;

    @Test
    public void Test01(){
        Student student = loginService.findStudent("201605010313");
        Teacher teacher = loginService.findTeacher("222");
        System.out.println(student);
        System.out.println(teacher);
    }
    @Test
    public void Test02(){
        List<Student> studentList = studentService.findAllStudentByList();
        for (Student s: studentList
             ) {
            System.out.println(s);
        }
    }

    @Test
    public void Test03(){
        Question question = questionService.findQuestionById(1);
        System.out.println(question);
    }

}


