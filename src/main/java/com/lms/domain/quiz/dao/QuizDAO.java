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
                        rset.getInt("quiz_Id")
                );

                quizList.add(quiz);
            }

        }
        return quizList;
    }

    // 퀴즈 상세 조회
    public QuizDTO findByQuizId(int id) throws SQLException {

        String query = QueryUtil.getQuery("quiz.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1,id);

            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new QuizDTO(
                        rset.getString("content"),
                        rset.getString("quiz_title"),
                        rset.getInt("quiz_Id")
                );

            }

        }
        return null;
    }

    // 퀴즈 등록
    public Long create(QuizDTO quiz) throws SQLException {

        String query = QueryUtil.getQuery("quiz.create");

        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, quiz.getQuizId());    // 순번 추가
            pstmt.setInt(2, quiz.getMafiaId());
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

    public int selectNextQuizId() throws SQLException {
        String query = QueryUtil.getQuery("quiz.selectNextQuizId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getInt(1);
            }
        }
        return 1;
    }

}
