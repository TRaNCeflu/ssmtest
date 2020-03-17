package com.zh.dao;

import com.zh.domain.Question;
import com.zh.domain.Score;

import java.util.List;

public interface IScoreDao {
    Score findScoreByTwoId(Integer questionId,String StudentId);
    List<Score> findScoreByStudentId(String studentId);
    int findScoreRightNum(Integer questionId);
    boolean insertScoreByStudent(Score score);
    boolean updateScoreByStudent(Score score);

    boolean deleteScoreBeforeDeleteStudent(String studentId);
    boolean updateScoreAfterQuestionUpdate(Question question);
}
