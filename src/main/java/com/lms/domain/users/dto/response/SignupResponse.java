package com.lms.domain.users.dto.response;

import com.lms.domain.users.constant.UserRole;

public class SignupResponse {
    private String username;
    private String name;
    private UserRole role;

    public SignupResponse(String username, String name, UserRole role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }
}
