package com.lms.domain.users.view;

import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.SignupResponse;

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

    public void displaySignupSuccess(SignupResponse response) {
        System.out.println("\n🎉 주민 등록이 성공적으로 완료되었습니다!");
        // Enum의 getDescription()을 사용해 '학생'으로 출력
        System.out.println("  환영합니다! " + response.getName() + "님은 이제 [" + response.getRole().getDescription() + "] 권한으로 마을을 이용하실 수 있습니다.");
        System.out.println("  초기 화면에서 [1] 로그인을 선택하여 마을에 입장해 주세요.");
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    public void displaySignupFailure(String errorMessage) {
        System.out.println("\n🚨 주민 등록 실패: " + errorMessage);
        System.out.println("  [!] 입력하신 정보를 다시 확인해 주시기 바랍니다.");
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }
}
