package com.zh.dao;

import com.zh.domain.User;
import com.zh.service.IUserService;
import org.apache.ibatis.session.SqlSessionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class UserDaoTest {

    @Autowired
    private IUserDao dao;

    @Resource
    private IUserService userService;

    @Test
    public void test01() throws Exception{
        User user = dao.selectUser(1);
        System.out.println(user);
    }
    @Test
    public void test02(){

        try{
            User user = this.userService.selectUser(1);
            System.out.println(user);
        }
        catch (SqlSessionException e){
            e.printStackTrace();
        }
    }
}
