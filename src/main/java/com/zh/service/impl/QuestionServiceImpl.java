package com.zh.service.impl;

import com.zh.dao.IQuestionDao;
import com.zh.domain.Question;
import com.zh.domain.QuestionForStudent;
import com.zh.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("questionService")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private IQuestionDao questionDao;

    @Override
    public List<Question> findAllQuestionList() {
        return questionDao.findAllQuestionList();
    }

    @Override
    public List<QuestionForStudent> findAllQuestionForStudent() {
        return questionDao.findAllQuestionForStudent();
    }

    @Override
    public Question findQuestionByIdForStudent(Integer id) {
        return questionDao.findQuestionByIdForStudent(id);
    }

    @Override
    public Question findQuestionById(Integer id) {
        return questionDao.findQuestionById(id);
    }

    @Override
    public boolean insertQuestion(Question question) {
        return questionDao.insertQuestion(question);
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        return questionDao.deleteQuestion(id);
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionDao.updateQuestion(question);
    }
}
