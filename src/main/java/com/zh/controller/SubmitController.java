package com.zh.controller;


import com.zh.common.bean.VResponse;
import com.zh.dao.IQuestionDao;
import com.zh.domain.Question;
import com.zh.domain.Score;
import com.zh.service.IQuestionService;
import com.zh.service.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Autowired
    private ISubmitService submitService;

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/findScoreByTwoId")
    public VResponse<Score> findScoreByTwoId(@RequestParam("questionId") Integer questionId,@RequestParam("studentId") String studentId){
        Score score = submitService.findScoreByTwoId(questionId,studentId);
        if(score != null){
            score.setQuestionAnswer("");
            return VResponse.success(score);
        }
        else{
            return VResponse.error(2,"未提交过此题");
        }
    }

    @PostMapping("/insertScoreByStudent")
    public VResponse<Object> insertScoreByStudent(@RequestBody Score score){
        Question question = questionService.findQuestionById(score.getQuestionId());

        score.setQuestionAnswer(question.getQuestionAnswer());
        score.setQuestionType(question.getQuestionType());
        score.setSubmitType(1);
        Date date = new Date();
        score.setSubmitTime(date);

        boolean isInsert = submitService.insertScoreByStudent(score);
        if(isInsert){
            return VResponse.success("提交成功");
        }
        else {
            return VResponse.error(2,"提交失败");
        }
    }
}
