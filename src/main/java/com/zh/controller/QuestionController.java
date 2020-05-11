package com.zh.controller;

import com.zh.common.bean.VResponse;
import com.zh.common.util.JdbcForSQLJU;
import com.zh.common.util.MatcherSQL;
import com.zh.domain.Question;
import com.zh.domain.QuestionForStudent;
import com.zh.domain.QuestionPlusSubCount;
import com.zh.domain.Score;
import com.zh.service.IQuestionService;
import com.zh.service.IStudentService;
import com.zh.service.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ISubmitService submitService;

    @Autowired
    private IStudentService studentService;
    @GetMapping("/findAllQuestionForStudent")
    public VResponse<List<QuestionForStudent>> findAllQuestionForStudent(@RequestParam("studentId") String studentId){
        List<QuestionForStudent> questionList = questionService.findAllQuestionForStudent();
        for(int i = 0; i<questionList.size();i++){
            Score score = submitService.findScoreByTwoId(questionList.get(i).getQuestionId(),studentId);
            if(score != null){
                if(score.getSubmitType() == 1){
                    questionList.get(i).setSubmitType("已提交");
                }else if(score.getSubmitType() == 2){
                    questionList.get(i).setSubmitType("未通过");
                }
                else if(score.getSubmitType() == 0){
                    questionList.get(i).setSubmitType("未提交");
                }
                else if(score.getSubmitType() == 4){
                    questionList.get(i).setSubmitType("题目已更新，请重新提交");
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

    @GetMapping("/findAllQuestion")
    public VResponse<List<QuestionPlusSubCount>> findAllQuestion(){
        List<Question> questionList = questionService.findAllQuestionList();
        int studentNum = studentService.findAllStudentCount();
        List<QuestionPlusSubCount> questionPlusSubCountList = new ArrayList<QuestionPlusSubCount>();
        for(int i = 0;i<questionList.size();i++){
            Question question = questionList.get(i);
            int rightNum = submitService.findScoreRightNum(question.getQuestionId());
            questionPlusSubCountList.add(new QuestionPlusSubCount(question.getQuestionId(),question.getQuestionType(),
                    question.getQuestionContent(), question.getQuestionAnswer(),studentNum,rightNum));
        }
        return VResponse.success(questionPlusSubCountList);
    }

    @GetMapping("/findQuestionByIdForStudent")
    public VResponse<Question> findQuestionByIdForStudent(@RequestParam("questionId")Integer id){
        Question question = questionService.findQuestionByIdForStudent(id);
        return VResponse.success(question);
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
        tmpAns = tmpAns.toLowerCase();
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
        boolean sqlCorrect = false;
        Question que = checkQuestionType(question);
        if(que.getQuestionType() == 1){
            if(JdbcForSQLJU.judgeSQLRightForSelect(que.getQuestionAnswer()) == 1){
                sqlCorrect = true;
            }else{
                return VResponse.error(3,"sql语句无法执行");
            }
        }else{
            String tableName = MatcherSQL.matchSql(que.getQuestionAnswer());
            if(tableName == null){
                return VResponse.error(3,"表名无法识别");
            }
            String nsql = MatcherSQL.changeSqlForAlter(que.getQuestionAnswer(),tableName,"##jtmp");
            Integer alterJudge = JdbcForSQLJU.judgeSQLRightForAlter(nsql,tableName);
            if(alterJudge == 1){
                sqlCorrect = true;
            }else if(alterJudge == -1){
                return VResponse.error(3,"不存在sql中用到的表");
            }else{
                return VResponse.error(3,"sql语句执行失败");
            }

        }
        if(sqlCorrect == false){
            return VResponse.error(3,"sql语句执行失败");
        }
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
        //检查题目是查还是增删改
        Question que = checkQuestionType(question);
        Question question1 = questionService.findQuestionById(que.getQuestionId());

        if(!question1.getQuestionAnswer().equals(que.getQuestionAnswer())){
            boolean sqlCorrect = false;
            if(que.getQuestionType() == 1){
                if(JdbcForSQLJU.judgeSQLRightForSelect(que.getQuestionAnswer()) == 1){
                    sqlCorrect = true;
                }else{
                    return VResponse.error(3,"sql语句无法执行");
                }
            }else{
                String tableName = MatcherSQL.matchSql(que.getQuestionAnswer());
                if(tableName == null){
                    return VResponse.error(3,"表名无法识别");
                }
                String nsql = MatcherSQL.changeSqlForAlter(que.getQuestionAnswer(),tableName,"##jtmp");
                Integer alterJudge = JdbcForSQLJU.judgeSQLRightForAlter(nsql,tableName);
                if(alterJudge == 1){
                    sqlCorrect = true;
                }else if(alterJudge == -1){
                    return VResponse.error(3,"不存在sql中用到的表");
                }else{
                    return VResponse.error(3,"sql语句执行失败");
                }
            }
            if(!sqlCorrect){
                return VResponse.error(3,"sql语句执行失败");
            }
        }


        //如果老师更新题目时答案变化，则将对应的question_answer在score表中更新,并将提交类型改为未提交
        if(!question1.getQuestionAnswer().equals(que.getQuestionAnswer())){
            submitService.updateScoreAfterQuestionUpdate(que);
        }
        boolean isUpdate = questionService.updateQuestion(que);
        if(isUpdate){
            return VResponse.success("更新成功");
        }
        else{
            return VResponse.error(2,"更新失败");
        }
    }
}
