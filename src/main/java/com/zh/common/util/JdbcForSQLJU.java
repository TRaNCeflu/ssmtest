package com.zh.common.util;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class JdbcForSQLJU {
    public static Integer judgeSQLRightForSelect(String sql){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        Integer param2Value = (Integer) jdbcTemplate.execute(
                con -> {
                    String storedProc = "{call judgeSQLRightForSelect(?,?)}";// 调用的sql
                    CallableStatement cs = con.prepareCall(storedProc);
                    cs.setString(1, sql);// 设置输入参数的值
                    cs.registerOutParameter(2, Types.INTEGER);// 注册输出参数的类型
                    return cs;
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();
                        return cs.getInt(2);// 获取输出参数的值
                    }
                });
        return param2Value;
    }

    public static Integer judgeSQLRightForAlter(String sql,String tableName){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        Integer param2Value = (Integer) jdbcTemplate.execute(
                con -> {
                    String storedProc = "{call judgeSQLRightForAlter(?,?,?)}";// 调用的sql
                    CallableStatement cs = con.prepareCall(storedProc);
                    cs.setString(1, sql);// 设置输入参数的值
                    cs.setString(2,tableName);
                    cs.registerOutParameter(3, Types.INTEGER);// 注册输出参数的类型
                    return cs;
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();
                        return cs.getInt(3);// 获取输出参数的值
                    }
                });
        return param2Value;
    }
}
