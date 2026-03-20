package com.lms.domain.users.constant;

public enum UserRole {
    ADMIN("관리자"),
    INSTRUCTOR("강사"),
    STUDENT("학생");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
