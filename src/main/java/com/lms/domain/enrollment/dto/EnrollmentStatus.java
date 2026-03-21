package com.lms.domain.enrollment.dto;

public enum EnrollmentStatus {
    WAITING("승인대기"),
    APPROVED("승인완료"),
    REJECTED("승인거절"),
    EXPELLED("퇴장");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
