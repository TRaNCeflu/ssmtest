package com.zh.controller;

import com.zh.common.bean.VResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class fileController {
    @PostMapping("/upload")
    public VResponse<Object> uploadFile(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return VResponse.success("上传成功");
    }
}
