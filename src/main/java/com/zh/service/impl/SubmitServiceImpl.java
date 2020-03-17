package com.zh.service.impl;

import com.zh.dao.IScoreDao;
import com.zh.domain.Question;
import com.zh.domain.Score;
import com.zh.service.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("submitService")
public class SubmitServiceImpl implements ISubmitService {
    @Autowired
    private IScoreDao scoreDao;

    @Override
    public Score findScoreByTwoId(Integer questionId, String StudentId) {
        return scoreDao.findScoreByTwoId(questionId,StudentId);
    }

    @Override
    public boolean insertScoreByStudent(Score score) {
        return scoreDao.insertScoreByStudent(score);
    }

    @Override
    public boolean updateScoreAfterQuestionUpdate(Question question) {
        return scoreDao.updateScoreAfterQuestionUpdate(question);
    }

    @Override
    public boolean updateScoreByStudent(Score score) {
        return scoreDao.updateScoreByStudent(score);
    }

    @Override
    public List<Score> findScoreByStudentId(String studentId) {
        return scoreDao.findScoreByStudentId(studentId);
    }

    @Override
    public int findScoreRightNum(Integer questionId) {
        return scoreDao.findScoreRightNum(questionId);
    }
}
