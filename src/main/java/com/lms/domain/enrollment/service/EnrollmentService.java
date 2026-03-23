package com.lms.domain.enrollment.service;

import com.lms.domain.enrollment.dao.EnrollmentDAO;
import com.lms.domain.enrollment.dto.EnrollmentDTO;
import com.lms.domain.enrollment.dto.Response.ApprovedEnrollmentResponse;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.enrollment.dto.Response.WaitingEnrollmentResponse;
import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final UserDAO userDAO;
    private final Connection connection;

    public EnrollmentService(Connection connection) {
        this.enrollmentDAO = new EnrollmentDAO(connection);
        this.userDAO = new UserDAO(connection);
        this.connection = connection;
    }


    public VerifyInviteCodeResponse findVillageByInviteCode(String inviteCode) {

        if (inviteCode == null) {
            throw new IllegalArgumentException("빈 코드를 입력하셨습니다. 올바른 코드를 입력해주세요.");
        }

        try {
            return enrollmentDAO.findVillageByInviteCode(inviteCode);
        } catch (SQLException e) {
            throw new RuntimeException("존재하는 마을이 없습니다.", e);
        }
    }

    public void submitEnrollment(long currentUserId, long villageId) {
        try {
            enrollmentDAO.insertIntoEnrollment(currentUserId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EnterVillageResponse> getApprovedVillages(long currentUserId) {

        try {
            List<EnterVillageResponse> responseList = enrollmentDAO.findActiveVillageByUserId(currentUserId);

            return responseList;

        } catch (SQLException e) {
            throw new RuntimeException("[DB error] 승인된 마을 목록을 조회하는 중 문제가 발생했습니다.", e);
        }
    }

    public List<EnterVillageResponse> getWaitingVillages(long currentUserId) {

        try {
            List<EnterVillageResponse> responseList = enrollmentDAO.findWaitingVillageByUserId(currentUserId);

            return responseList;

        } catch (SQLException e) {
            throw new RuntimeException("[DB error] 마을 목록을 조회하는 중 문제가 발생했습니다.", e);
        }
    }

    public boolean isApprovedUser(long currentUserId, long villageId) {
        try {
            return enrollmentDAO.checkApprovedEnrollment(currentUserId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("[isApprovedUser] 수강생 검증 중 서버 오류가 발생했습니다.", e);
        }
    }


    // comment, 정현이 코드
    //  ===== 강사용 수강생 관리 기능 추가 =====

    // re, 마을 별 대기 중인 신청 목록 조회
    public List<WaitingEnrollmentResponse> findWaitingEnrollmentList(long villageId) {
        try {
            return enrollmentDAO.findWaitingEnrollmentList(villageId);
        } catch (SQLException e) {
            throw new RuntimeException("대기 중인 수강 신청 목록 조회 실패", e);
        }
    }
    //re, 마을 별 승인된 신청 목록 조회
    public List<ApprovedEnrollmentResponse> findApprovedEnrollmentList(long villageId) {
        try {
            List<EnrollmentDTO> enrollmentDTOList = enrollmentDAO.findApprovedEnrollmentList(villageId);

            if (enrollmentDTOList == null || enrollmentDTOList.isEmpty()) {
                throw new IllegalArgumentException("[❌] 해당 마을에 [승인] 상태의 유저가 존재하지 않습니다.");
            }

            List<ApprovedEnrollmentResponse> responseList = new ArrayList<>();

            for (EnrollmentDTO dto : enrollmentDTOList) {

                String realName = userDAO.findNameById(dto.getUserId());

                responseList.add(new ApprovedEnrollmentResponse(
                        dto.getEnrollmentId(),
                        realName,
                        dto.getStatus(),
                        dto.getAppliedAt()
                ));
            }
            return responseList;

        } catch (SQLException e) {
            throw new RuntimeException("승인된 수강생 목록 조회 실패", e);
        }
    }

    public void approveEnrollment(long villageId, long enrollmentId) {
        try {
            enrollmentDAO.approveEnrollment(villageId, enrollmentId);
        } catch (SQLException e) {
            throw new RuntimeException("승인 처리 중 DB 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void rejectEnrollment(long villageId, long enrollmentId) {
        try {
            enrollmentDAO.rejectEnrollment(villageId, enrollmentId);
        } catch (SQLException e) {
            throw new RuntimeException("거절 처리 중 DB 오류가 발생했습니다." + e.getMessage());
        }
    }

    public void expelEnrollment(long villageId, long enrollmentId) {
        try {
            enrollmentDAO.expelEnrollment(villageId, enrollmentId);
        } catch (SQLException e) {
            throw new RuntimeException("추방 처리 중 DB 오류", e);
        }
    }
}
