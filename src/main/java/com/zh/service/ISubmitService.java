package com.zh.service;

import com.zh.domain.Score;

public interface ISubmitService {

    Score findScoreByTwoId(Integer questionId, String StudentId);
    boolean insertScoreByStudent(Score score);
}
