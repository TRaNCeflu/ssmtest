package com.zh.service;

import com.zh.domain.Question;
import com.zh.domain.QuestionForStudent;

import java.util.List;

public interface IQuestionService {
    List<Question> findAllQuestionList();
    List<QuestionForStudent> findAllQuestionForStudent();
    Question findQuestionByIdForStudent(Integer id);
    Question findQuestionById(Integer id);
    boolean insertQuestion(Question question);
    boolean deleteQuestion(Integer id);
    boolean updateQuestion(Question question);
}
