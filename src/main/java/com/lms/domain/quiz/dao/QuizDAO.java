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
    public QuizDTO findByQuizId(long quizId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1,quizId);

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
            pstmt.setLong(1, quiz.getQuizId());
            pstmt.setObject(2, quiz.getUserId());
            //null 값은 Object로
            pstmt.setObject(3, quiz.getMafiaId());
            pstmt.setString(4, quiz.getTitle());
            pstmt.setString(5, quiz.getContent());
            pstmt.setString(6, quiz.getAnswer());

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
    public Long deleteByMafia(Long quiz, Long userId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByMafia");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quiz);
            pstmt.setLong(2,userId);
            pstmt.executeUpdate();
        }
        return 0L;
    }


    // 강사가 삭제
    public Long deleteByInstructorAndAdmin(Long quiz) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByinstructorAndAdmin");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quiz);
            pstmt.executeUpdate();
        }
        return 0L;
    }

    public int updateQuizByMafia(Long quizId, String title, String content, String answer,Long userId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByMafia");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);
            pstmt.setLong(5, userId);

            int rs = pstmt.executeUpdate();

            if (rs == 0 ) {
                throw new RuntimeException("본인이 작성한 퀴즈만 수정할 수 있습니다.");
            }
            return rs;
        }
    }

    public int updateQuizByInstructorAndAdmin(Long quizId, String title, String content, String answer) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByInstructorAndAdmin");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);

            int rs = pstmt.executeUpdate();

            if (rs == 0 ) {
                throw new RuntimeException("본인이 작성한 퀴즈만 수정할 수 있습니다.");
            }
            return rs;
        }
    }

    // 오늘의 퀴즈 있는지 조회
    public QuizDTO selectTodayQuiz() throws SQLException {
        String query = QueryUtil.getQuery("quiz.selectTodayQuiz");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return new QuizDTO(
                        rset.getLong("quiz_id"),
                        rset.getString("quiz_title"),
                        rset.getString("content"),
                        rset.getString("answer")
                );
            }
        }
        return null;
    }

}
