package com.lms.domain.quizSubmission.service;

import com.lms.domain.quiz.dao.QuizDAO;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.quizSubmission.dao.QuizSubDAO;
import com.lms.domain.quizSubmission.dto.QuizSubDTO;
import com.lms.global.common.UserSession;

import java.sql.Connection;
import java.sql.SQLException;

public class QuizSubService {

    private final QuizSubDAO quizsubDAO;
    private final Connection connection;
    private final QuizDAO quizDAO;


    public QuizSubService(Connection connection) {
        this.quizsubDAO = new QuizSubDAO(connection);
        this.connection = connection;
        this.quizDAO = new QuizDAO(connection);
    }

    public boolean submitAnswer(String submitAnswer, long villageId) {
        try {
            Long userId = UserSession.getLoggedInUser().getUserId();

            // 오늘 퀴즈 가져오기
            QuizDTO todayQuiz = quizDAO.selectTodayQuiz(villageId);

            // 이미 제출했는지 체크
            boolean exists = quizsubDAO.alreadysubmit(todayQuiz.getQuizId(), userId);
            if (exists) {
                throw new RuntimeException("이미 제출한 퀴즈입니다.");
            }

            // 정답 비교
            boolean isCorrect = todayQuiz.getAnswer().equals(submitAnswer);

            // 제출
            QuizSubDTO quizSub = new QuizSubDTO(null,todayQuiz.getQuizId(), userId, submitAnswer, isCorrect, null);
            quizsubDAO.submit(quizSub);

            return isCorrect;

        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 제출 실패 : " + e.getMessage());
        }
    }

}
