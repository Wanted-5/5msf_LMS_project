package com.lms.domain.quiz.dao;

import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    private final Connection connection;

    public QuizDAO(Connection connection) {
        this.connection = connection;
    }

    // 모든 퀴즈 조회
    public List<QuizDTO> findAll() throws SQLException {

        String query = QueryUtil.getQuery("quiz.findAll");
        List<QuizDTO> quizList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()) {
                QuizDTO quiz = new QuizDTO(
                        rset.getString("content"),
                        rset.getString("quiz_title"),
                        rset.getLong("quiz_Id")
                );

                quizList.add(quiz);
            }

        }
        return quizList;
    }

    // 퀴즈 상세 조회
    public QuizDTO findByQuizId(long id) throws SQLException {

        String query = QueryUtil.getQuery("quiz.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1,id);

            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new QuizDTO(
                        rset.getString("content"),
                        rset.getString("quiz_title"),
                        rset.getLong("quiz_Id")
                );

            }

        }
        return null;
    }

    // 퀴즈 등록
    public Long create(QuizDTO quiz) throws SQLException {

        String query = QueryUtil.getQuery("quiz.create");

        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, quiz.getQuizId());    // 순번 추가
            pstmt.setLong(2, quiz.getMafiaId());
            pstmt.setString(3, quiz.getTitle());
            pstmt.setString(4, quiz.getContent());
            pstmt.setString(5, quiz.getAnswer());

            pstmt.executeUpdate();

            ResultSet rset = pstmt.getGeneratedKeys();
            if(rset.next()) {
                return rset.getLong(1);
            }
        }
        return null;
    }

    // DB에 차고차곡
    public Long selectNextQuizId() throws SQLException {
        String query = QueryUtil.getQuery("quiz.selectNextQuizId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getLong(1);
            }
        }
        return 1L;
    }

    // 마피아가 퀴즈 삭제
    public Long deleteByMafia(long quiz, long userId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByMafia");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quiz);
            pstmt.setLong(2,userId);
            pstmt.executeUpdate();
        }
        return 0L;
    }


    // 강사가 삭제
    public Long deleteByInstructor(long quiz) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByinstructor");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quiz);
            pstmt.executeUpdate();
        }
        return 0L;
    }

    // 관리자에 의해 퀴즈 삭제
    public Long deleteByAdmin(long quiz) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByAdmin");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quiz);
            pstmt.executeUpdate();
        }
        return 0L;
    }

    public Long updateQuizByMafia(long quizId, String title, String content, String answer,long userId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByMafia");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);
            pstmt.setLong(5, userId);
            pstmt.executeUpdate();
        }

        return 0L;
    }

    public Long updateQuizByInstructor(long quizId, String title, String content, String answer) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByInstructor");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);
            pstmt.executeUpdate();
        }

        return 0L;
    }

    public Long updateQuizByAdmin(long quizId, String title, String content, String answer) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByAdmin");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);
            pstmt.executeUpdate();
        }

        return 0L;
    }

}
