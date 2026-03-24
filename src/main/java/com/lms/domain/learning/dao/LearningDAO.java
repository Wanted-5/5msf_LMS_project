package com.lms.domain.learning.dao;

import com.lms.domain.learning.dto.LearningDTO;
import com.lms.domain.learning.dto.LearningStatus;
import com.lms.domain.learning.dto.reseponse.LearningSectionResponse;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LearningDAO {

    private final Connection connection;

    public LearningDAO(Connection connection) {
        this.connection = connection;
    }

    public List<LearningDTO> findByUserIdAndVillageId(long userId, long villageId) throws SQLException {

        List<LearningDTO> learningDTOList = new ArrayList<>();

        String query = QueryUtil.getQuery("learning.findByUserIdAndVillageId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, villageId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                learningDTOList.add(convertToDTO(rs));
            }
        }
        return learningDTOList;
    }

    public List<LearningDTO> findByUserIdAndVillageIdAndStatus(long userId, long villageId, LearningStatus status) throws SQLException {
        List<LearningDTO> learningDTOList = new ArrayList<>();

        String query = QueryUtil.getQuery("learning.findByUserIdAndVillageIdAndStatus");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, villageId);
            pstmt.setString(3, status.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    learningDTOList.add(convertToDTO(rs));
                }
            }
        }
        return learningDTOList;
    }

    public int insertIntoBeforeLearning(long sectionId, long villageId) throws SQLException {
        String query = QueryUtil.getQuery("learning.insertIntoBeforeLearning");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, sectionId);
            pstmt.setLong(2, villageId);

            int insertedCount = pstmt.executeUpdate();

            return insertedCount;
        } catch (SQLException e) {
            throw new SQLException("[DAO Error] 강의 초기 생성 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public void updateStatusInProgress(long userId, long sectionId) throws SQLException {
        String query = QueryUtil.getQuery("learning.updateStatusToInProgress");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, sectionId);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows <= 0) {
                System.out.println("  🚨 [시스템] 수강 상태 변경에 실패했거나, 이미 수강을 시작한 강의입니다.");
            }
            else {
                System.out.println("  ▶️ [시스템] 강의 시청 중 이탈! (상태: 수강 중)");
            }
        }
    }

    public void updateStatusCompleted(long userId, long sectionId) throws SQLException {
        String query = QueryUtil.getQuery("learning.updateStatusToCompleted");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, sectionId);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows <= 0) {
                System.out.println("  🚨 [시스템] 수강 상태 변경에 실패했거나, 이미 수강한 강의입니다.");
            }
            else {
                System.out.println("  ▶️ [시스템] 강의 수강 완료! (상태: 수강완료)");
            }
        }
    }

    public List<LearningSectionResponse> findLearningDetailsByStatus(long userId, long villageId, LearningStatus status) throws SQLException {

        List<LearningSectionResponse> resultList = new ArrayList<>();

        String query = QueryUtil.getQuery("learning.findLearningDetailsByStatus");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, villageId);
            pstmt.setString(3, status.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 🌟 뷰에서 이중 for문 돌릴 필요 없이, DB에서 조인되어 온 결과를 여기서 한 번에 포장합니다!
                    resultList.add(new LearningSectionResponse(
                            rs.getLong("learning_id"),
                            rs.getLong("section_id"),
                            rs.getInt("chap_no"),
                            rs.getString("section_name"),
                            LearningStatus.valueOf(rs.getString("status"))
                    ));
                }
            }
        }
        return resultList;
    }

    // 새로운 학생들에게 기존 강의 수강전으로 입력
    public int insertExistingSectionsForNewStudent(long userId, long villageId) throws SQLException {
        String query = QueryUtil.getQuery("learning.insertExistingSectionsForNewStudent");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, villageId);
            pstmt.setLong(3, villageId);

            return pstmt.executeUpdate();
        }
    }


    // =================== 내부 편의 메서드 ==================
    private LearningDTO convertToDTO(ResultSet rs) throws SQLException {
        return new LearningDTO(
                rs.getLong("learning_id"),
                rs.getLong("user_id"),
                rs.getLong("village_id"),
                rs.getLong("section_id"),
                LearningStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
