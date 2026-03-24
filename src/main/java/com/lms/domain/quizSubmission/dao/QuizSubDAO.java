package com.lms.domain.quizSubmission.dao;

import com.lms.domain.quizSubmission.dto.QuizSubDTO;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizSubDAO {

    private final Connection connection;

    public QuizSubDAO(Connection connection) {
        this.connection = connection;
    }

    // 이미 제출했는지 확인
    public boolean alreadysubmit(long quizId, long userId) throws SQLException {

        String query = QueryUtil.getQuery("quizsub.alreadySubmission");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, quizId);
            pstmt.setLong(2, userId);
            ResultSet rset = pstmt.executeQuery();
            if(rset.next()) {
                return rset.getInt(1) > 0;
            }
        }
        return false;
    }

    // 퀴즈 제출한 사람을 quiz-submission 테이블에 가져다 박움
    public int submit(QuizSubDTO quizSub) throws SQLException {

        String query = QueryUtil.getQuery("quizsub.submit");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, quizSub.getQuizId());
            pstmt.setLong(2, quizSub.getUserId());
            pstmt.setString(3, quizSub.getSubmitAnswer());
            pstmt.setBoolean(4, quizSub.getCorrect());
            return pstmt.executeUpdate();

        }

    }
}
