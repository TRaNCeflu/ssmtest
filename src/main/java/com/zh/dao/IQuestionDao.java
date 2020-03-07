package com.zh.dao;

import com.zh.domain.Question;

import java.util.List;

public interface IQuestionDao {
    List<Question> findAllQuestionList();
    Question findQuestionById(Integer id);
    boolean insertQuestion(Question question);
    boolean deleteQuestion(Integer id);
    boolean updateQuestion(Question question);
}
