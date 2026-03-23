package com.lms.domain.enrollment.service;

import com.lms.domain.enrollment.dao.EnrollmentDAO;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final Connection connection;

    public EnrollmentService(Connection connection) {
        this.enrollmentDAO = new EnrollmentDAO(connection);
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

    // ===== 강사용 수강생 관리 기능 추가 =====

    public List<Map<String, Object>> findWaitingEnrollmentList(long villageId) {
        try {
            return enrollmentDAO.findWaitingEnrollmentList(villageId);
        } catch (SQLException e) {
            throw new RuntimeException("대기 중인 수강 신청 목록 조회 실패", e);
        }
    }

    public List<Map<String, Object>> findApprovedEnrollmentList(long villageId) {
        try {
            return enrollmentDAO.findApprovedEnrollmentList(villageId);
        } catch (SQLException e) {
            throw new RuntimeException("승인된 수강생 목록 조회 실패", e);
        }
    }

    public Map<String, Object> findEnrollmentManageTarget(long villageId, long enrollmentId) {
        try {
            return enrollmentDAO.findEnrollmentManageTarget(villageId, enrollmentId);
        } catch (SQLException e) {
            throw new RuntimeException("신청 번호 조회 실패", e);
        }
    }

    public void approveEnrollment(long villageId, long enrollmentId) {
        try {
            int result = enrollmentDAO.approveEnrollment(villageId, enrollmentId);
            if (result <= 0) {
                throw new RuntimeException("승인 처리 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException("승인 처리 중 DB 오류", e);
        }
    }

    public void rejectEnrollment(long villageId, long enrollmentId) {
        try {
            int result = enrollmentDAO.rejectEnrollment(villageId, enrollmentId);
            if (result <= 0) {
                throw new RuntimeException("거절 처리 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException("거절 처리 중 DB 오류", e);
        }
    }

    public void expelEnrollment(long villageId, long enrollmentId) {
        try {
            int result = enrollmentDAO.expelEnrollment(villageId, enrollmentId);
            if (result <= 0) {
                throw new RuntimeException("추방 처리 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException("추방 처리 중 DB 오류", e);
        }
    }
    public  boolean verifyVillageApproval(long userId, long villageId) {
        try {
            return enrollmentDAO.existsApprovedEnrollment(userId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("승인된 마을 여부를 확인하는 중 오류가 발생했습니다.", e);
        }
    }

    public void promoteWaitingStudentToInstructor(long enrollmentId) {
        try {
            connection.setAutoCommit(false);

            Map<String, Object> target = enrollmentDAO.findWaitingEnrollmentTarget(enrollmentId);

            if (target == null) {
                throw new RuntimeException("해당 신청 번호의 대기 중인 유저가 없습니다.");
            }

            long userId = ((Number) target.get("userId")).longValue();
            long villageId = ((Number) target.get("villageId")).longValue();

            com.lms.domain.users.dao.UserDAO userDAO =
                    new com.lms.domain.users.dao.UserDAO(connection);

            userDAO.updateUserRole(userId, com.lms.domain.users.dto.UserRole.INSTRUCTOR);

            int result = enrollmentDAO.approveEnrollment(villageId, enrollmentId);
            if (result <= 0) {
                throw new RuntimeException("승인 처리 실패");
            }

            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("강사 승격 처리 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Map<String, Object>> findAllWaitingEnrollmentList() {
        try {
            return enrollmentDAO.findAllWaitingEnrollmentList();
        } catch (SQLException e) {
            throw new RuntimeException("전체 대기 중인 수강 신청 목록 조회 실패", e);
        }
    }

}
