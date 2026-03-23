package com.lms.domain.enrollment.dao;

import com.lms.domain.enrollment.dto.EnrollmentDTO;
import com.lms.domain.enrollment.dto.EnrollmentStatus;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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