package com.zh.domain;

public class QuestionForStudent {

    private Integer questionId;
    private Integer questionType;
    private String questionContent;
    private String submitType;

    public QuestionForStudent() {
    }

    public QuestionForStudent(Integer questionId, Integer questionType, String questionContent, String submitType) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.submitType = submitType;
    }

    @Override
    public String toString() {
        return "QuestionForStudent{" +
                "questionId=" + questionId +
                ", questionType=" + questionType +
                ", questionContent='" + questionContent + '\'' +
                ", submitType=" + submitType +
                '}';
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
