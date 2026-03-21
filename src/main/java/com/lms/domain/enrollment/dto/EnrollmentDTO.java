package com.lms.domain.enrollment.dto;

import java.time.LocalDateTime;

public class EnrollmentDTO {

    private Long enrollmentId;
    private Long villageId;
    private Long userId;
    private EnrollmentStatus status;
    private LocalDateTime appliedAt;

    public EnrollmentDTO(Long enrollmentId, Long villageId, Long userId, EnrollmentStatus status, LocalDateTime appliedAt) {
        this.enrollmentId = enrollmentId;
        this.villageId = villageId;
        this.userId = userId;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public Long getUserId() {
        return userId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}
