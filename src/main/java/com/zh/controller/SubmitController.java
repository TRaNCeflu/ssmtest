package com.zh.controller;


import com.zh.common.bean.VResponse;
import com.zh.domain.Question;
import com.zh.domain.QuestionForStudent;
import com.zh.domain.Score;
import com.zh.service.IQuestionService;
import com.zh.service.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Autowired
    private ISubmitService submitService;

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/findScoreByStudentId")
    public VResponse<List<Score>> findScoreByStudentId(@RequestParam("studentId") String studentId){
        List<Score> scoreList = submitService.findScoreByStudentId(studentId);
        return VResponse.success(scoreList);
    }

    @GetMapping("/findScoreDetailForStudentId")
    public VResponse<List<QuestionForStudent>> findScoreDetailForStudentId(@RequestParam("studentId") String studentId){
        List<QuestionForStudent> questionList = questionService.findAllQuestionForStudent();
        for(int i = 0; i<questionList.size();i++){
            Score score = submitService.findScoreByTwoId(questionList.get(i).getQuestionId(),studentId);
            if(score != null){
                questionList.get(i).setStudentAnswer(score.getStudentAnswer());
                questionList.get(i).setSubmitTime(score.getSubmitTime());
                if(score.getSubmitType() == 1){
                    questionList.get(i).setSubmitType("已提交");
                }else if(score.getSubmitType() == 2){
                    questionList.get(i).setSubmitType("未通过");
                }
                else if(score.getSubmitType() == 0){
                    questionList.get(i).setSubmitType("未提交");
                }
                else if(score.getSubmitType() == 4){
                    questionList.get(i).setSubmitType("题目更新后未提交");
                }
                else{
                    questionList.get(i).setSubmitType("已通过");
                }
            }
            else
                questionList.get(i).setSubmitType("未提交");
        }
        return VResponse.success(questionList);
    }

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

    @PutMapping("/updateScoreByStudent")
    public VResponse<Object> updateScoreByStudent(@RequestBody Score score){
        Question question = questionService.findQuestionById(score.getQuestionId());

        score.setQuestionAnswer(question.getQuestionAnswer());
        score.setQuestionType(question.getQuestionType());
        score.setSubmitType(1);
        Date date = new Date();
        score.setSubmitTime(date);

        boolean isUpdate = submitService.updateScoreByStudent(score);
        if(isUpdate){
            return VResponse.success("更新成功");
        }else {
            return VResponse.error(2,"更新失败");
        }
    }
}
