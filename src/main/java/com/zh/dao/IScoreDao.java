package com.zh.dao;

import com.zh.domain.Score;

public interface IScoreDao {
    Score findScoreByTwoId(Integer questionId,String StudentId);
    boolean insertScoreByStudent(Score score);
    boolean updateScoreByStudent(Score score);
    boolean deleteScoreBeforeDeleteStudent(String studentId);
}
