package com.lms.domain.enrollment.dto.Response;

import com.lms.domain.enrollment.dto.EnrollmentStatus;

import java.time.LocalDateTime;

// 특정 마을의 Waiting 상태인 유저들만 담아서 보여주는 Response
public class WaitingEnrollmentResponse {
    private Long enrollmentId;
    private String villageName;
    private String name; // user 실명
    private EnrollmentStatus status;
    private LocalDateTime appliedAt;

    public WaitingEnrollmentResponse(long enrollmentId, String villageName, String name, EnrollmentStatus status, LocalDateTime appliedAt) {
        this.enrollmentId = enrollmentId;
        this.villageName = villageName;
        this.name = name;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public String getVillageName() {
        return villageName;
    }

    public String getName() {
        return name;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}
