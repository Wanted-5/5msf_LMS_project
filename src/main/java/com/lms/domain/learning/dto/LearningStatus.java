package com.lms.domain.learning.dto;

public enum LearningStatus {
    BEFORE_LEARNING("수강전"),
    IN_PROGRESS("수강중"),
    COMPLETED("수강완료");

    private final String description;

    LearningStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
