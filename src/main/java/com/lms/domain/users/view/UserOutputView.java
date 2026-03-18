package com.lms.domain.users.view;

import com.lms.domain.users.dto.response.LoginResponse;

public class UserOutputView {

    public void displayLoginSuccess(LoginResponse response) {
        System.out.println("\n🎉 로그인에 성공했습니다!");
        System.out.println("  " + response.getName() + "[" + response.getNickname() + "]님 어서오세요.");
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    public void displayLoginFailure(String errorMessage) {
        System.out.println("\n🚨 로그인 실패: " + errorMessage);
        System.out.println("  [🚨] 아이디와 비밀번호를 다시 확인해주세요.");
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }
}
