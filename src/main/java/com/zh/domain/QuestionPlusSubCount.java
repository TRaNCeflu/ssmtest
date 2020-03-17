package com.zh.domain;

public class QuestionPlusSubCount {
    private Integer questionId;
    private Integer questionType;
    private String questionContent;
    private String questionAnswer;
    private Integer studentNum;
    private Integer rightNum;

    public QuestionPlusSubCount() {
    }

    public QuestionPlusSubCount(Integer questionId, Integer questionType, String questionContent, String questionAnswer, Integer studentNum, Integer rightNum) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.questionAnswer = questionAnswer;
        this.studentNum = studentNum;
        this.rightNum = rightNum;
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

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    @Override
    public String toString() {
        return "QuestionPlusSubCount{" +
                "questionId=" + questionId +
                ", questionType=" + questionType +
                ", questionContent='" + questionContent + '\'' +
                ", questionAnswer='" + questionAnswer + '\'' +
                ", studentNum=" + studentNum +
                ", rightNum=" + rightNum +
                '}';
    }
}
