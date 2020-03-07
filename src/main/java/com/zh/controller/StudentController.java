package com.zh.controller;

import com.zh.common.bean.VResponse;
import com.zh.domain.Student;
import com.zh.service.IStudentService;
import com.zh.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;


    @GetMapping("/findStudentList")
    public VResponse<List<Student>> findAllStudentList(){
        List<Student> studentList = studentService.findAllStudentByList();
        return VResponse.success(studentList);
    }

    @PostMapping("/insertStudent")
    public VResponse<Object> insertstudent(@RequestBody Student student){
        boolean isInsert = studentService.insertStudent(student);
        if(isInsert){
            return VResponse.success("添加成功");
        }else{
            return VResponse.error(2,"添加失败");
        }
        //return VResponse.success("添加成功");
    }

    @PostMapping("/deleteStudent")
    public VResponse<Object> deleteStudent(@RequestBody Student student){
        boolean isDelete = studentService.deleteStudent(student);
        if(isDelete){
            return VResponse.success("删除成功");
        }
        else{
            return VResponse.error(2,"删除失败");
        }
    }

    @PutMapping("/updateStudent")
    public VResponse<Object> updateStudent(@RequestBody Student student){
        boolean isUpdate = studentService.updateStudent(student);
        if(isUpdate){
            return VResponse.success("更新成功");
        }
        else{
            return VResponse.error(2,"更新失败");
        }
    }

}
