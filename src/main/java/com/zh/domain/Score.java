package com.zh.domain;
import java.sql.Timestamp;
import java.util.Date;

public class Score {
    private Integer questionId;
    private String studentId;
    private String questionAnswer;
    private String studentAnswer;
    private Integer submitType;
    private Date submitTime;
    private Integer questionType;

    public Score() {
    }

    public Score(Integer questionId, String studentId, String questionAnswer, String studentAnswer, Integer submitType, Date submitTime,Integer questionType) {
        this.questionId = questionId;
        this.studentId = studentId;
        this.questionAnswer = questionAnswer;
        this.studentAnswer = studentAnswer;
        this.submitType = submitType;
        this.submitTime = submitTime;
        this.questionType = questionType;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Integer getSubmitType() {
        return submitType;
    }

    public void setSubmitType(Integer submitType) {
        this.submitType = submitType;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "Score{" +
                "questionId=" + questionId +
                ", studentId=" + studentId +
                ", questionAnswer='" + questionAnswer + '\'' +
                ", studentAnswer='" + studentAnswer + '\'' +
                ", submitType=" + submitType +
                ", submitTime=" + submitTime +
                ", questionType=" + questionType +
                '}';
    }
}
