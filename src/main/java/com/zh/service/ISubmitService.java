package com.zh.service;

import com.zh.domain.Question;
import com.zh.domain.Score;

import java.util.List;

public interface ISubmitService {

    Score findScoreByTwoId(Integer questionId, String StudentId);
    boolean insertScoreByStudent(Score score);
    boolean updateScoreAfterQuestionUpdate(Question question);
    boolean updateScoreByStudent(Score score);
    List<Score> findScoreByStudentId(String studentId);
}
