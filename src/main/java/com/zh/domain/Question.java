package com.zh.domain;

public class Question {
    private Integer questionId;
    private Integer questionType;
    private String questionContent;
    private String questionAnswer;

    public Question() {
    }

    public Question(Integer questionId, Integer questionType, String questionContent, String questionAnswer) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.questionAnswer = questionAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionType=" + questionType +
                ", questionContent='" + questionContent + '\'' +
                ", questionAnswer='" + questionAnswer + '\'' +
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

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
