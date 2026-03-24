package com.lms.domain.enrollment.dao;

import com.lms.domain.enrollment.dto.EnrollmentDTO;
import com.lms.domain.enrollment.dto.EnrollmentStatus;
import com.lms.domain.enrollment.dto.Response.ApprovedEnrollmentResponse;
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

    public void insertIntoEnrollment(long currentUserId, long villageId, EnrollmentStatus status) throws SQLException {

        String query = QueryUtil.getQuery("enrollment.insert");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, currentUserId);
            pstmt.setString(3, status.name());

            int rest = pstmt.executeUpdate();

            if (rest == 0) {
                throw new SQLException("[error] 마을 신청 실패, DB 에러 발생");
            }
        }
    }
    // [조회] 현재 학생이 가입 '승인(APPROVED)'된 마을 목록만 찾기
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

    // 특정 마을 대기 인원 조회
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

    // 특정 마을 승인 유저 조회
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

    //comment, 정현이 코드 리펙토링 OK
    // ===== 강사용 수강생 관리 기능 추가 =====

    // 대기 상태인 학생들 전체 조회 기능
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

    // 특정 마을의 승인된 유저만 조회하는 기능
    public List<EnrollmentDTO> findApprovedEnrollmentList(long villageId) throws SQLException {
        List<EnrollmentDTO> enrollmentDTOList = new ArrayList<>();
        String query = QueryUtil.getQuery("enrollment.findApprovedByVillageId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollmentDTOList.add(convertToDTO(rs));
                }
            }
        }
        return enrollmentDTOList;
    }

    // 승인 기능
    public int approveEnrollment(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.approve");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);
            int updatedRows = pstmt.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("[approveEnrollment error] 승인으로 변경 실패");
            }
            return updatedRows;
        }
    }

    // 거절 기능
    public int rejectEnrollment(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.reject");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("[rejectEnrollment error] 거절 실패");
            }
            return updatedRows;
        }
    }

    // 추방 기능
    public int expelEnrollment(long villageId, long enrollmentId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.expel");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, enrollmentId);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("[expelEnrollment error] 거절 실패");
            }
            return updatedRows;
        }
    }

    // 특정 유저의 특정 마을 신청 내역을 조회합니다.
    public EnrollmentDTO findByUserIdAndVillageId(long userId, long villageId) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.findByUserIdAndVillageId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 이전에 만들어둔 DTO 변환 메서드를 활용합니다.
                    return convertToDTO(rs);
                }
            }
        }
        return null; // 내역이 없으면 null 반환
    }

    // 강사 권한 업데이트
    public int updateStatus(long enrollmentId, EnrollmentStatus status) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.updateStatus");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, status.name());
            pstmt.setLong(2, enrollmentId);

            return pstmt.executeUpdate();
        }
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