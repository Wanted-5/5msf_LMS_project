package com.lms.domain.enrollment.controller;

<<<<<<< HEAD
import com.lms.domain.enrollment.dto.Response.ApprovedEnrollmentResponse;
=======
import com.lms.domain.enrollment.dao.EnrollmentDAO;
>>>>>>> eb58bcbe2084f6f92bafc69820cff2a086fed614
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.enrollment.dto.Response.WaitingEnrollmentResponse;
import com.lms.domain.enrollment.service.EnrollmentService;
import com.lms.domain.users.dto.UserDTO;

import java.util.List;

import java.sql.SQLException;
import java.util.Map;
import java.util.List;

public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    // 마을 검증 로직
    public VerifyInviteCodeResponse verifyInviteCode(String inviteCode) throws Exception {
        return service.findVillageByInviteCode(inviteCode);
    }

    // 신청하기 enrollment에 insert하기
    public void submitEnrollment(long currentUserId, long villageId) throws Exception{
        service.submitEnrollment(currentUserId, villageId);
    }

    public List<EnterVillageResponse> getApprovedVillages(long currentUserId) {
        return service.getApprovedVillages(currentUserId);
    }

    public List<EnterVillageResponse> getWaitingVillage(long currentUserId) throws Exception {
        return service.getWaitingVillages(currentUserId);
    }

    // 사용자가 해당 마을의 승인된 유저인지 검증
    public boolean verifyVillageApproval(long currentUserId, long villageId) {
        return service.isApprovedUser(currentUserId, villageId);
    }

    // comment, 정현이 코드
    public List<WaitingEnrollmentResponse> findWaitingEnrollmentList(long villageId) {
        return service.findWaitingEnrollmentList(villageId);
    }

    public List<ApprovedEnrollmentResponse> findApprovedEnrollmentList(long villageId) {
        return service.findApprovedEnrollmentList(villageId);
    }

    public void approveEnrollment(long villageId, long enrollmentId) {
        service.approveEnrollment(villageId, enrollmentId);
    }

    public void rejectEnrollment(long villageId, long enrollmentId) {
        service.rejectEnrollment(villageId, enrollmentId);
    }

    public void expelEnrollment(long villageId, long enrollmentId) {
        service.expelEnrollment(villageId, enrollmentId);
    }

<<<<<<< HEAD
    public void enterVillageByAdmin(long villageId) {
        service.enterVillageByAdmin(villageId);
=======
    public boolean verifyVillageApproval(long userId, long villageId) {
        return service.verifyVillageApproval(userId, villageId);
    }
    public List<Map<String, Object>> findAllWaitingEnrollmentList() {
        return service.findAllWaitingEnrollmentList();
    }

    public void promoteWaitingStudentToInstructor(long enrollmentId) {
        service.promoteWaitingStudentToInstructor(enrollmentId);
>>>>>>> eb58bcbe2084f6f92bafc69820cff2a086fed614
    }
}
