package com.zh.controller;

import com.zh.common.bean.VResponse;
import com.zh.domain.Teacher;
import com.zh.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private ITeacherService teacherService;

    @GetMapping("/findAllTeacherList")
    public VResponse<List<Teacher>> findAllTeacherList(){
        List<Teacher> teachers = teacherService.findAllTeacherList();
        return VResponse.success(teachers);
    }

    @PostMapping("insertTeacher")
    public VResponse<Object> insertTeacher(@RequestBody Teacher teacher){
        boolean isInsert = teacherService.insertTeacher(teacher);
        if(isInsert){
            return VResponse.success("添加成功");
        }
        else {
            return VResponse.error(2,"添加失败");
        }
    }

    @DeleteMapping("deleteTeacher")
    public VResponse<Object> deleteTeacher(@RequestParam("teacherId") String id){
        boolean isDelete = teacherService.deleteTeacher(id);
        if(isDelete){
            return VResponse.success("删除成功");
        }
        else {
            return VResponse.error(2,"删除失败");
        }
    }
}
