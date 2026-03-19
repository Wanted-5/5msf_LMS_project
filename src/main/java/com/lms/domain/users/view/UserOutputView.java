package com.lms.domain.users.view;

import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.MyPageResponse;
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

    public void displayLogoutSuccess() {
        System.out.println("\n👋 안전하게 로그아웃 되었습니다. 다음에 또 방문해 주세요!");
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    public void displayMyPageFailure(String errorMessage) {
        System.out.println("\n🚨 거울이 흐려져 모습을 확인할 수 없습니다.");
        System.out.println("  [오류] " + errorMessage);
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    public void displayMyPageSuccess(MyPageResponse response) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🪞 거울 속 나의 모습                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("  ▶ 이    름 : " + response.getName());
        System.out.println("  ▶ 닉 네 임 : " + response.getNickname());
        System.out.println("  ▶ 이 메 일 : " + response.getEmail());

        // 💡 실무 디테일: 전화번호나 한 줄 소개가 null일 경우를 대비한 삼항 연산자 처리
        String phone = (response.getPhoneNumber() != null) ? response.getPhoneNumber() : "등록되지 않음";
        System.out.println("  ▶ 전화번호 : " + phone);

        String intro = (response.getIntroduction() != null) ? response.getIntroduction() : "등록된 소개가 없습니다.";
        System.out.println("  ▶ 한줄소개 : " + intro);

        System.out.println("  ▶ 소속마을 : 🚧 추후 업데이트 예정");
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }
}
