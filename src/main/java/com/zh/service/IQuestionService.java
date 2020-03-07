package com.zh.service;

import com.zh.domain.Question;

import java.util.List;

public interface IQuestionService {
    List<Question> findAllQuestionList();
    List<Question> findAllQuestionForStudent();
    Question findQuestionByIdForStudent(Integer id);
    Question findQuestionById(Integer id);
    boolean insertQuestion(Question question);
    boolean deleteQuestion(Integer id);
    boolean updateQuestion(Question question);
}
