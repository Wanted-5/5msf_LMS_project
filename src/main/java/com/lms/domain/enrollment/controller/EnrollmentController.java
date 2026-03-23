package com.lms.domain.enrollment.controller;

import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.enrollment.service.EnrollmentService;

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
}
