package com.zh.service.impl;

import com.zh.dao.IScoreDao;
import com.zh.domain.Score;
import com.zh.service.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
