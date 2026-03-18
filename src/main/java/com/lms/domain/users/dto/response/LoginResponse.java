package com.lms.domain.users.dto.response;

import com.lms.domain.users.constant.UserRole;

public class LoginResponse {
    private Long userId;        // 다음 로직 처리를 위한 식별자
    private String name;        // 유저 실명 (예: 이학생)
    private String nickname; // 소속 마을 (예: 백엔드 마스터 트랙 1기)
    private UserRole role;      // 권한 (메뉴 분기용)

    public LoginResponse(Long userId, String name, String nickname, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public UserRole getRole() {
        return role;
    }
}
