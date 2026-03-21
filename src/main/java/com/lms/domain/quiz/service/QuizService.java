package com.lms.domain.quiz.service;

import com.lms.domain.quiz.dao.QuizDAO;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.quizSubmission.dao.QuizSubDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class QuizService {

    private final QuizDAO quizDAO;
    private final Connection connection;

    public QuizService(Connection connection) {
        this.quizDAO = new QuizDAO(connection);
        this.connection = connection;
    }

    public List<QuizDTO> findAllQuiz() {

        try {
            return quizDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 전체 조회 중 Error 발생!!" + e);
        }

    }

    public QuizDTO findByQuizId(int id) {

        try {
            return quizDAO.findByQuizId(id);
        } catch (SQLException e) {
            throw new RuntimeException("강좌 상세 조회 중 오류 발생!!" + e);
        }

    }

    public Long createQuiz(QuizDTO newQuiz) {

        try {
            int nextId = quizDAO.selectNextQuizId();  // 추가
            newQuiz.setQuizId(nextId);
            return quizDAO.create(newQuiz);
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 등록 중 Error 발생 !!!" + e.getMessage());
        }

    }
}
