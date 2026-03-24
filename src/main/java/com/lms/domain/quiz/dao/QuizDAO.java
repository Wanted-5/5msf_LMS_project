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

    // 모든 퀴즈 조회 v, 추가
    public List<QuizDTO> findAllByVillageId(long villageId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.findAllByVillageId");
        List<QuizDTO> quizList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()) {
                QuizDTO quiz = new QuizDTO(
                        rset.getString("content"),
                        rset.getString("quiz_title"),
                        rset.getLong("quiz_Id"),
                        rset.getLong("village_id")
                );

                quizList.add(quiz);
            }

        }
        return quizList;
    }

    // 퀴즈 상세 조회, v 추가
    public QuizDTO findByQuizId(long quizId, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quizId);
            pstmt.setLong(2, villageId);

            ResultSet rset = pstmt.executeQuery();

            if(rset.next()) {
                return new QuizDTO(
                        rset.getString("content"),
                        rset.getString("quiz_title"),
                        rset.getLong("quiz_Id"),
                        rset.getLong("village_id")
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
            // 🌟 [추가] INSERT 쿼리의 4번째 '?' 인 village_id 바인딩
            pstmt.setLong(4, quiz.getVillageId());
            pstmt.setString(5, quiz.getTitle());
            pstmt.setString(6, quiz.getContent());
            pstmt.setString(7, quiz.getAnswer());

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

    // 마피아가 퀴즈 삭제, v 추가
    public int deleteByMafia(Long quiz, Long userId, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByMafia");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,quiz);
            pstmt.setLong(2, villageId);
            pstmt.setLong(3,userId);
            pstmt.setLong(4, villageId);

            int rs = pstmt.executeUpdate();

            if(rs == 0) {
                throw new RuntimeException("본인이 작성한 퀴즈만 삭제 할 수 있습니다");
            }
            return rs;
        }
    }


    // 강사가 삭제 v, 추가 완
    public int deleteByInstructorAndAdmin(Long quiz, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.deleteByinstructorAndAdmin");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, quiz);
            pstmt.setLong(2, villageId);
            int rs = pstmt.executeUpdate();
            return rs;
        }
    }

    // 🌟 [수정] 파라미터에 long villageId 추가
    public int updateQuizByMafia(Long quizId, String title, String content, String answer, Long userId, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByMafia");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);
            // 🌟 [추가] 쿼리 메인 조건의 village_id
            pstmt.setLong(5, villageId);
            pstmt.setLong(6, userId);
            // 🌟 [추가] 쿼리 서브쿼리 조건의 village_id
            pstmt.setLong(7, villageId);

            int rs = pstmt.executeUpdate();

            if (rs == 0 ) {
                throw new RuntimeException("본인이 작성한 퀴즈만 수정할 수 있습니다.");
            }
            return rs;
        }
    }

    // 🌟 [수정] 파라미터에 long villageId 추가
    public int updateQuizByInstructorAndAdmin(Long quizId, String title, String content, String answer, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("quiz.updateByInstructorAndAdmin");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, answer);
            pstmt.setLong(4, quizId);
            // 🌟 [추가] 쿼리 조건의 village_id
            pstmt.setLong(5, villageId);

            int rs = pstmt.executeUpdate();

            if (rs == 0 ) {
                throw new RuntimeException("해당 마을의 퀴즈를 수정할 권한이 없거나, 존재하지 않는 퀴즈입니다.");
            }
            return rs;
        }
    }

    // 오늘의 퀴즈 있는지 조회
    public QuizDTO selectTodayQuiz(long villageId) throws SQLException {
        String query = QueryUtil.getQuery("quiz.selectTodayQuiz");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // 🌟 [추가] 쿼리에 추가된 village_id = ? 바인딩
            pstmt.setLong(1, villageId);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return new QuizDTO(
                        rset.getLong("quiz_id"),
                        rset.getString("quiz_title"),
                        rset.getLong("village_id"),
                        rset.getString("content"),
                        rset.getString("answer")
                );
            }
        }
        return null;
    }

}
