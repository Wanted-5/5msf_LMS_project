package com.lms.domain.enrollment.dao;

import com.lms.domain.enrollment.dto.EnrollmentDTO;
import com.lms.domain.enrollment.dto.EnrollmentStatus;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.enrollment.dto.Response.WaitingEnrollmentResponse;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentDAO {

    private final Connection connection;

    public EnrollmentDAO(Connection connection) {
        this.connection = connection;
    }

    public VerifyInviteCodeResponse findVillageByInviteCode(String inviteCode) throws SQLException {

        String query = QueryUtil.getQuery("village.findByInviteCode");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, inviteCode);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                return new VerifyInviteCodeResponse(
                        rset.getLong("village_id"),
                        rset.getString("village_name"),
                        rset.getString("description"),
                        rset.getDate("start_date") != null ? rset.getDate("start_date").toLocalDate() : null
                );
            }
        }
        return null;
    }

    public void insertIntoEnrollment(long currentUserId, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("enrollment.insert");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, currentUserId);
            pstmt.setString(3, String.valueOf(EnrollmentStatus.WAITING));

            int rest = pstmt.executeUpdate();

            if (rest == 0) {
                throw new SQLException("[error] 마을 신청 실패, DB 에러 발생");
            }
        }
    }

    public List<EnterVillageResponse> findActiveVillageByUserId(long currentUserId) throws SQLException {

        List<EnterVillageResponse> enterVillageList = new ArrayList<>();

        String query = QueryUtil.getQuery("enrollment.findApprovedVillagesByUserId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, currentUserId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                enterVillageList.add(new EnterVillageResponse(
                        rs.getLong("village_id"),
                        rs.getString("village_name"),
                        EnrollmentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("applied_at").toLocalDateTime()
                ));
            }
        }
        return enterVillageList;
    }

    public List<EnterVillageResponse> findWaitingVillageByUserId(long currentUserId) throws SQLException {

        List<EnterVillageResponse> waitingVillageResponseList = new ArrayList<>();

        String query = QueryUtil.getQuery("enrollment.findWaitingVillagesByUserId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, currentUserId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                waitingVillageResponseList.add(new EnterVillageResponse(
                        rs.getLong("village_id"),
                        rs.getString("village_name"),
                        EnrollmentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("applied_at").toLocalDateTime()
                ));

            }
        }
        return waitingVillageResponseList;
    }

    public boolean checkApprovedEnrollment(long currentUserId, long villageId) throws SQLException {

        String query = QueryUtil.getQuery("enrollment.checkApprovedStatus");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, currentUserId);
            pstmt.setLong(2, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    //comment, 정현이 코드
    // ===== 강사용 수강생 관리 기능 추가 =====

    public List<WaitingEnrollmentResponse> findWaitingEnrollmentList(long villageId) throws SQLException {
        List<WaitingEnrollmentResponse> waitingResponseList = new ArrayList<>();
        String query = QueryUtil.getQuery("enrollment.findWaitingResponseByVillageId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    Timestamp appliedAt = rs.getTimestamp("applied_at");

                    waitingResponseList.add(new WaitingEnrollmentResponse(
                            rs.getLong("enrollment_id"),
                            rs.getString("village_name"),
                            rs.getString("name"),
                            EnrollmentStatus.valueOf(rs.getString("status")),
                            appliedAt != null ? appliedAt.toLocalDateTime() : null
                    ));
                }
            }
        }
        return waitingResponseList;
    }

    public List<Map<String, Object>> findApprovedEnrollmentList(long villageId) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = QueryUtil.getQuery("enrollment.findApprovedByVillageId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(convertJoinRowToMap(rs));
                }
            }
        }
        return list;
    }

    public Map<String, Object> findEnrollmentManageTarget(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.findManageTargetById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return convertJoinRowToMap(rs);
                }
            }
        }
        return null;
    }

    public void approveEnrollment(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.approve");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);
            int updatedRows = pstmt.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("[approveEnrollment error] 승인으로 변경 실패");
            } else {
                System.out.println("🎉 승인 성공");
            }
        }
    }

    public int rejectEnrollment(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.reject");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);
            return pstmt.executeUpdate();
        }
    }

    public int expelEnrollment(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.expel");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);
            return pstmt.executeUpdate();
        }
    }

    private Map<String, Object> convertJoinRowToMap(ResultSet rs) throws SQLException {
        Map<String, Object> row = new HashMap<>();

        Timestamp appliedAt = rs.getTimestamp("applied_at");

        row.put("enrollmentId", rs.getLong("enrollment_id"));
        row.put("villageId", rs.getLong("village_id"));
        row.put("userId", rs.getLong("user_id"));
        row.put("userName", rs.getString("username"));
        row.put("status", rs.getString("status"));
        row.put("appliedAt", appliedAt != null ? appliedAt.toLocalDateTime() : null);

        return row;
    }


    // ======================= 내부 편의 메서드 =============================
    private EnrollmentDTO convertToDTO(ResultSet rs) throws SQLException {
        return new EnrollmentDTO(
                rs.getLong("enrollment_id"),
                rs.getLong("village_id"),
                rs.getLong("user_id"),
                EnrollmentStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("applied_at").toLocalDateTime()
        );
    }
}