package com.lms.domain.enrollment.dto.Response;

import com.lms.domain.enrollment.dto.EnrollmentStatus;

import java.time.LocalDateTime;

// 특정 마을의 승인된 상태인 유저들만 담는 response
public class ApprovedEnrollmentResponse {
    private Long enrollmentId;
    private String name;   // 학생 실명
    private EnrollmentStatus status;
    private LocalDateTime enrolledAt;

    public ApprovedEnrollmentResponse(Long enrollmentId, String name, EnrollmentStatus status, LocalDateTime enrolledAt) {
        this.enrollmentId = enrollmentId;
        this.name = name;
        this.status = status;
        this.enrolledAt = enrolledAt;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public String getName() {
        return name;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }
}
