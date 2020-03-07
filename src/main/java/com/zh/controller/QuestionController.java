package com.zh.controller;

import com.zh.common.bean.VResponse;
import com.zh.domain.Question;
import com.zh.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @GetMapping("/findAllQuestion")
    public VResponse<List<Question>> findAllQuestion(){
        List<Question> questionList = questionService.findAllQuestionList();
        return VResponse.success(questionList);
    }

    @GetMapping("/findQuestionById")
    public VResponse<Question> findQuestionById(@RequestParam("questionId") Integer id){
        Question question = questionService.findQuestionById(id);
        if(question == null){
            return VResponse.error(2,"查询不到此ID");
        }
        else {
            return VResponse.success(question);
        }
    }

    private Question checkQuestionType(Question question){
        Question newQue = new Question(question.getQuestionId(),question.getQuestionType(),
                question.getQuestionContent(),question.getQuestionAnswer());

        String tmpAns = newQue.getQuestionAnswer();
        tmpAns = tmpAns.trim();
        if(tmpAns.charAt(0) == 's'){
            newQue.setQuestionType(1);
        }
        else{
            newQue.setQuestionType(2);
        }
        newQue.setQuestionAnswer(tmpAns);
        return newQue;
    }

    @PostMapping("/insertQuestion")
    public VResponse<Object> insertQuestion(@RequestBody Question question){
        Question que = checkQuestionType(question);
        boolean isInsert = questionService.insertQuestion(que);
        if(isInsert){
           return VResponse.success("添加成功");
        }
        else{
           return VResponse.error(2,"添加失败");
        }
    }

    @DeleteMapping("/deleteQuestion")
    public VResponse<Object> deleteQuestion(@RequestParam("questionId") Integer id){
        boolean isDelete = questionService.deleteQuestion(id);
        if(isDelete){
            return VResponse.success("删除成功");
        }
        else{
            return VResponse.error(2,"删除失败");
        }
    }

    @PutMapping("/updateQuestion")
    public VResponse<Object> updateQuestion(@RequestBody Question question){
        Question que = checkQuestionType(question);
        boolean isUpdate = questionService.updateQuestion(que);
        if(isUpdate){
            return VResponse.success("更新成功");
        }
        else{
            return VResponse.error(2,"更新失败");
        }
    }
}
