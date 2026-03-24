package com.lms.domain.enrollment.service;

import com.lms.domain.enrollment.dao.EnrollmentDAO;
import com.lms.domain.enrollment.dto.EnrollmentDTO;
import com.lms.domain.enrollment.dto.EnrollmentStatus;
import com.lms.domain.enrollment.dto.Response.ApprovedEnrollmentResponse;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.enrollment.dto.Response.WaitingEnrollmentResponse;
import com.lms.domain.learning.dao.LearningDAO;
import com.lms.domain.learning.service.LearningService;
import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.common.UserSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final UserDAO userDAO;
    private final LearningService learningService;
    private final Connection connection;

    public EnrollmentService(Connection connection) {
        this.enrollmentDAO = new EnrollmentDAO(connection);
        this.userDAO = new UserDAO(connection);
        this.connection = connection;
        this.learningService = new LearningService(connection);
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

            UserDTO user = userDAO.findById(currentUserId);

            EnrollmentStatus initialStatus = (user.getRole() == UserRole.INSTRUCTOR ||
                    user.getRole() == UserRole.ADMIN) ? EnrollmentStatus.APPROVED : EnrollmentStatus.WAITING;

            enrollmentDAO.insertIntoEnrollment(currentUserId, villageId, initialStatus);
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

            EnrollmentDTO enrollment = enrollmentDAO.findById(enrollmentId);
            long userId = enrollment.getUserId();

            learningService.insertExistingSectionsForNewStudent(userId, villageId);

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
<<<<<<< HEAD

    public void enterVillageByAdmin(long villageId) {
        long currentUserId = UserSession.getLoggedInUser().getUserId(); //

        try {
            // 특정 마을 입장 여부 확인
            EnrollmentDTO existing = enrollmentDAO.findByUserIdAndVillageId(currentUserId, villageId);

            if (existing == null) {
                // APPROVED로 즉시 가입
                enrollmentDAO.insertIntoEnrollment(currentUserId, villageId, EnrollmentStatus.APPROVED);
            }

        } catch (SQLException e) {
            throw new RuntimeException("[Service Error] 관리자 직권 입장 처리 중 오류 발생", e);
=======
    public  boolean verifyVillageApproval(long userId, long villageId) {
        try {
            return enrollmentDAO.existsApprovedEnrollment(userId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("승인된 마을 여부를 확인하는 중 오류가 발생했습니다.", e);
>>>>>>> eb58bcbe2084f6f92bafc69820cff2a086fed614
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
