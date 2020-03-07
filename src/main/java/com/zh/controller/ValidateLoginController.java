package com.zh.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.common.bean.VResponse;
import com.zh.domain.Student;
import com.zh.domain.Teacher;
import com.zh.service.ILoginService;
import com.zh.service.PasswordService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.ws.RequestWrapper;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class ValidateLoginController {

    @Resource
    private ILoginService loginService;

    @Autowired
    private PasswordService passwordService;

    @PutMapping("/changeTeacherPassword")
    public VResponse<Object> changeTeacherPassword(@RequestBody Teacher teacher){
        boolean isChange = passwordService.changeTeacherPassword(teacher);
        if(isChange){
            return VResponse.success("修改成功");
        }else{
            return VResponse.error(2,"修改失败");
        }
    }

    @PutMapping("/changeStudentPassword")
    public VResponse<Object> changeStudentPassword(@RequestBody Student student){
        boolean isChange = passwordService.changeStudentPassword(student);
        if(isChange){
            return VResponse.success("修改成功");
        }
        else{
            return VResponse.error(2,"修改失败");
        }
    }

    @PostMapping("/validateteacherlogin")
    public Object loginOfTeacher(@RequestBody Teacher teacher){
        Teacher teacherInDB =  loginService.findTeacher(teacher.getTeacherId());
        JSONObject jsonObject = new JSONObject();
        if (teacherInDB == null) {
            jsonObject.put("code",0);
            jsonObject.put("error", "用户不存在");
        } else if (!teacher.getPassword().equals(teacherInDB.getPassword())) {
            jsonObject.put("code",1);
            jsonObject.put("error", "密码不正确");
        } else {
            jsonObject.put("code",2);
            jsonObject.put("msg","登录成功");
            jsonObject.put("login", teacherInDB);
        }
        return jsonObject;
    }

    @PostMapping("/validatestudentlogin")
    public Object loginOfStudent(@RequestBody Student student){
        Student studentInDB = loginService.findStudent(student.getStudentId());
        JSONObject jsonObject = new JSONObject();
        if (studentInDB == null) {
            jsonObject.put("code",0);
            jsonObject.put("error", "用户不存在");
        } else if (!student.getPassword().equals(studentInDB.getPassword())) {
            jsonObject.put("code",1);
            jsonObject.put("error", "密码不正确");
        } else {
            jsonObject.put("code",2);
            jsonObject.put("msg","登录成功");
            jsonObject.put("login", studentInDB);
        }
        return jsonObject;
    }
}
