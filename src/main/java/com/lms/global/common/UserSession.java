package com.lms.global.common;

import com.lms.domain.users.dto.response.LoginResponse;

public class UserSession {

    private static LoginResponse loggedInUser;

    // 로그인 유지 (세션에 저장하는 용도)
    public static void setLoggedInUser(LoginResponse user) {
        loggedInUser = user;
    }

    // 로그아웃 처리
    public static void logout() {
        loggedInUser = null;
    }

    // 현재 로그인한 유저 정보 가져오기 = 게터
    public static LoginResponse getLoggedInUser() {
        return loggedInUser;
    }

    // 현재 로그인 여부 (true면 로그인)
    public static Boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
