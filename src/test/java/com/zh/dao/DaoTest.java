package com.zh.dao;

import com.zh.common.bean.VResponse;
import com.zh.common.util.MatcherSQL;
import com.zh.domain.Question;
import com.zh.domain.Student;
import com.zh.domain.Teacher;
import com.zh.service.ILoginService;
import com.zh.service.IQuestionService;
import com.zh.service.IStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.*;
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

    @Autowired
    private IQuestionDao questionDao;

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
        Question question = questionDao.findQuestionByIdForStudent(1004);
        System.out.println(question);
    }



    @Test
    public void Test04(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        jdbcTemplate.execute("update [user] set sex = 1 where id = 1");
    }

    private String getTableNameForAlter(String sql){
        String[] tmp =  sql.split(" ");
        //System.out.println(tmp[1]);
        return tmp[1];
    }

    @Test
    public void Test06(){
        String sql = "insert into ccc values(1,'neo','password')";
        String tableName = MatcherSQL.matchSql(sql);
        String nsql = MatcherSQL.changeSqlForAlter(sql,tableName,"##jtmp");
        System.out.println(tableName);
        System.out.println(nsql);
    }

    @Test
    public void Test05(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        Integer param2Value = (Integer) jdbcTemplate.execute(
                con -> {
                    String storedProc = "{call judgeSQLRight(?,?)}";// 调用的sql
                    CallableStatement cs = con.prepareCall(storedProc);
                    cs.setString(1, "select * from ques");// 设置输入参数的值
                    cs.registerOutParameter(2, Types.INTEGER);// 注册输出参数的类型
                    return cs;
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();
                        return cs.getInt(2);// 获取输出参数的值
                    }
                });
        System.out.println(param2Value);

    }

}


