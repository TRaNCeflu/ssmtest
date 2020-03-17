package com.zh.controller;


import com.zh.common.bean.VResponse;
import com.zh.common.util.JdbcForSQLJU;
import com.zh.common.util.MatcherSQL;
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
    // 0 未通过 1 已通过 2 sql语句无法执行 3 sql语句执行后结果比对错误 4 sql语句增删查改类型错误 5 sql语句操作的表错误 6 标准答案无法执行联系老师
    private int judgeSQLForStudent(Score score){
        //判断是 select 还是 alter 类型
        if(score.getQuestionType() == 1){
            //判断sql开头是否是select
            if(score.getStudentAnswer().startsWith("select")){
                //直接调用存储过程进行判断
                Integer selectJudge = JdbcForSQLJU.submitJudgeForSelect(score.getStudentAnswer(),score.getQuestionAnswer());
                if(selectJudge == -2){
                    return 2;
                }else if(selectJudge == -1){
                    return 3;
                }else if(selectJudge == 1 ){
                    return 1;
                }else if(selectJudge == -3){
                    return 6;
                } else{
                    return 3;
                }
            }else{
                return 4;
            }
        }else{
            if(score.getStudentAnswer().startsWith("update") ||
                score.getStudentAnswer().startsWith("insert") ||
                score.getStudentAnswer().startsWith("delete")) {
                String teacherTableName = MatcherSQL.matchSql(score.getQuestionAnswer());
                String studentTableName = MatcherSQL.matchSql(score.getStudentAnswer());
                //判断要操作的表是否一致
                if(!teacherTableName.equals(studentTableName)){
                    return 5;
                }else{
                    String teacherSql = MatcherSQL.changeSqlForAlter(score.getQuestionAnswer(),teacherTableName,"##atmp2");
                    String studentSql = MatcherSQL.changeSqlForAlter(score.getStudentAnswer(),studentTableName,"##atmp1");
                    Integer alterJudge = JdbcForSQLJU.submitJudgeForAlter(studentTableName,teacherSql,studentSql);
                    if(alterJudge == -3){
                        return 6;
                    }else if(alterJudge == -2){
                        return 2;
                    }else if(alterJudge == -1){
                        return 5;
                    }else if(alterJudge == 1){
                        return 1;
                    }else{
                        return 3;
                    }
                }
            }else {
                return 4;
            }
        }
    }

    @PostMapping("/insertScoreByStudent")
    public VResponse<Object> insertScoreByStudent(@RequestBody Score score){
        Question question = questionService.findQuestionById(score.getQuestionId());

        score.setStudentAnswer(score.getStudentAnswer().trim().toLowerCase());
        score.setQuestionAnswer(question.getQuestionAnswer());
        score.setQuestionType(question.getQuestionType());
        score.setSubmitType(1);
        Date date = new Date();
        score.setSubmitTime(date);

        // 0 未通过 1 已通过 2 sql语句无法执行 3 sql语句执行后结果比对错误 4 sql语句增删查改类型错误
        int result = judgeSQLForStudent(score);
        if(result == 0){
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"未通过");
        }else if(result == 1){
            score.setSubmitType(3);
            submitService.insertScoreByStudent(score);
            return VResponse.success("恭喜！回答正确");
        }else if(result == 2){
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"sql语句无法执行! 请检查语法错误");
        }else if(result == 3){
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"sql语句执行后结果比对错误！请检查是否理解题意");
        }else if(result == 5){
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"sql语句操作的表错误");
        }else if(result == 6){
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"标准答案可能错误！请联系老师");
        } else{
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"sql语句增删查改类型错误！请检查题目类型");
        }
    }

    @PutMapping("/updateScoreByStudent")
    public VResponse<Object> updateScoreByStudent(@RequestBody Score score){
        Question question = questionService.findQuestionById(score.getQuestionId());

        score.setStudentAnswer(score.getStudentAnswer().trim().toLowerCase());
        score.setQuestionAnswer(question.getQuestionAnswer());
        score.setQuestionType(question.getQuestionType());
        score.setSubmitType(1);
        Date date = new Date();
        score.setSubmitTime(date);

        // 0 未通过 1 已通过 2 sql语句无法执行 3 sql语句执行后结果比对错误 4 sql语句增删查改类型错误
        int result = judgeSQLForStudent(score);
        if(result == 0){
            score.setSubmitType(2);
            submitService.updateScoreByStudent(score);
            return VResponse.error(2,"未通过");
        }else if(result == 1){
            score.setSubmitType(3);
            submitService.updateScoreByStudent(score);
            return VResponse.success("恭喜！回答正确");
        }else if(result == 2){
            score.setSubmitType(2);
            submitService.updateScoreByStudent(score);
            return VResponse.error(2,"sql语句无法执行! 请检查语法错误");
        }else if(result == 3){
            score.setSubmitType(2);
            submitService.updateScoreByStudent(score);
            return VResponse.error(2,"sql语句执行后结果比对错误！请检查是否理解题意");
        }else if(result == 5){
            score.setSubmitType(2);
            submitService.updateScoreByStudent(score);
            return VResponse.error(2,"sql语句操作的表错误");
        }else if(result == 6){
            score.setSubmitType(2);
            submitService.insertScoreByStudent(score);
            return VResponse.error(2,"标准答案可能错误！请联系老师");
        }else{
            score.setSubmitType(2);
            submitService.updateScoreByStudent(score);
            return VResponse.error(2,"sql语句增删查改类型错误！请检查题目类型");
        }
    }
}
