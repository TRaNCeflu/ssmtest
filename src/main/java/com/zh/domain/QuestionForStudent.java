package com.zh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class QuestionForStudent {

    private Integer questionId;
    private Integer questionType;
    private String questionContent;
    private String submitType;
    private String studentAnswer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submitTime;

    public QuestionForStudent(Integer questionId, Integer questionType, String questionContent, String submitType, String studentAnswer, Date submitTime) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.submitType = submitType;
        this.studentAnswer = studentAnswer;
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "QuestionForStudent{" +
                "questionId=" + questionId +
                ", questionType=" + questionType +
                ", questionContent='" + questionContent + '\'' +
                ", submitType='" + submitType + '\'' +
                ", studentAnswer='" + studentAnswer + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }

    public QuestionForStudent() {
    }

    public Date getSubmitTime() {
        return submitTime;
    }



    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }
}
