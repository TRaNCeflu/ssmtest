package com.zh.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherSQL {

    public static void main(String[] args) {

        //INSERT INTO 表名称 VALUES (值1, 值2,....)
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        System.out.println( matchSql("insert into ccc values(1,'neo','password')") );
        System.out.println( matchSql("insert into ddd(id,name,password) values(1,'neo','password')") );
        //UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        System.out.println( matchSql("update eee set name = 'neo' where id = 1 ").trim() );
        //DELETE FROM 表名称 WHERE 列名称 = 值
        System.out.println( matchSql("delete from fff where id = 1 ").trim() );

        String sql = "insert into ccc values(1,'neo','password')";
        String oldTablName = matchSql(sql);
        System.out.println(oldTablName);
        System.out.println(changeSqlForAlter(sql,oldTablName,"##jtmp"));
    }

    public static String changeSqlForAlter(String sql,String oldTableName,String newTableName){
        String tmp = sql.replaceFirst(oldTableName,newTableName);
        return tmp;
    }

    //传入的sql语句前后无空格
    public static String matchSql(String sql){
        Matcher matcher = null;

        //INSERT INTO 表名称 VALUES (值1, 值2,....)
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        if( sql.startsWith("insert") ){
            matcher = Pattern.compile("insert\\sinto\\s(.+)\\svalues.*").matcher(sql);
            if(matcher.find()) {
                String tmp = matcher.group(1);
                boolean flag = false;
                for (int i = 0; i < tmp.length(); i++) {
                    if (tmp.charAt(i) == '(') {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    matcher = Pattern.compile("insert\\sinto\\s(.+)\\(.*\\)\\s.*").matcher(sql);
                    if(matcher.find()){
                        return matcher.group(1);
                    }
                } else {
                    return matcher.group(1);
                }
            }
        }
        //UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        if( sql.startsWith("update") ){
            matcher = Pattern.compile("update\\s(.+)set\\s.*").matcher(sql);
            if(matcher.find()){
                return matcher.group(1).trim();
            }
        }
        //DELETE FROM 表名称 WHERE 列名称 = 值
        if( sql.startsWith("delete") ){
            matcher = Pattern.compile("delete\\sfrom\\s(.+)where\\s(.*)").matcher(sql);
            if(matcher.find()){
                return matcher.group(1).trim();
            }
        }
        return null;
    }
}
