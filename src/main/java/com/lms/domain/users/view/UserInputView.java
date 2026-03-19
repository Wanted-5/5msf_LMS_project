package com.lms.domain.users.view;

import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.request.SignupRequest;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.MyPageResponse;
import com.lms.domain.users.dto.response.MyPageUpdateResponse;
import com.lms.domain.users.dto.response.SignupResponse;
import com.lms.global.common.UserSession;

import java.sql.SQLException;
import java.util.Scanner;

public class UserInputView {

    private final UserController userController;
    private final UserOutputView userOutputView;
    Scanner sc = new Scanner(System.in);

    public UserInputView(UserController userController, UserOutputView userOutputView) {
        this.userController = userController;
        this.userOutputView = userOutputView;
    }

    public void displayInitialMenu() {

        while (true) {
            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                              ║");
            System.out.println("║      ____  ______   _____ ____  _    _ _____   _____  ____   ║");
            System.out.println("║     / __ \\|  ____| / ____/ __ \\| |  | |  __ \\ / ____||  __|  ║");
            System.out.println("║    | |  | | |__   | |   | |  | | |  | | |__) | (___  | |__   ║");
            System.out.println("║    | |  | |  __|  | |   | |  | | |  | |  _  / \\___ \\ |  __|  ║");
            System.out.println("║    | |__| | |     | |___| |__| | |__| | | \\ \\ ____) || |__   ║");
            System.out.println("║     \\____/|_|      \\_____\\____/ \\____/|_|  \\_\\_____/ |____   ║");
            System.out.println("║                                                              ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║         🌱 당신의 성장이 시작되는 곳, [ Of Course ] 🌱       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("      [ 1 ] 로그인 (입장하기)");
            System.out.println("      [ 2 ] 회원가입 (주민 등록)");
            System.out.println("      [ 0 ] 시스템 종료");
            System.out.println();
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 메뉴의 번호를 입력해주세요 : ");

            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    loginProcess();
                    break;
                case "2":
                    signupProcess();
                    break;
                case "0":
                    System.out.println("\n────────────────────────────────────────────────────────────────");
                    System.out.println("  🏠 마을을 떠나시는군요. 당신의 성장을 항상 응원하겠습니다!");
                    System.out.println("  [ 시스템을 안전하게 종료합니다... ]");
                    System.out.println("────────────────────────────────────────────────────────────────");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n  [🚨] 올바른 번호(0, 1, 2)를 입력해주세요.");
            }
        }

    }


    private void loginProcess() {
        System.out.println("\n[ 🔐 로그인 ]");
        System.out.print("  ▶ 아이디: ");
        String username = sc.nextLine();

        System.out.print("  ▶ 비밀번호: ");
        String password = sc.nextLine();

        try {
            LoginResponse response = userController.loginProcess(username, password);

            UserSession.setLoggedInUser(response);

            userOutputView.displayLoginSuccess(response);

            displayMyPageMenu();

            // TODO: response.getRole()을 확인 후 강사 메뉴 / 학생 메뉴로 이동하는 로직이 추가!


        } catch (Exception e) {
            userOutputView.displayLoginFailure(e.getMessage());
        }
    }

    private void signupProcess() {

        try {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║               🌱 Of Course 마을 주민 등록증 발급                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            System.out.print("  ▶ 사용할 아이디: ");
            String username = sc.nextLine();

            System.out.print("  ▶ 비밀번호: ");
            String password = sc.nextLine();

            System.out.print("  ▶ 이메일: ");
            String email = sc.nextLine();

            System.out.print("  ▶ 실명(이름): ");
            String name = sc.nextLine();

            System.out.print("  ▶ 사용할 닉네임: ");
            String nickname = sc.nextLine();

            System.out.print("  ▶ 전화번호 (예: 010-1234-5678): ");
            String phoneNumber = sc.nextLine();

            System.out.print("  ▶ 거주지 주소: ");
            String address = sc.nextLine();

            System.out.print("  ▶ 성별 (M: 남성, F: 여성): ");
            String genderInput = sc.nextLine();
            boolean isFemale = genderInput.equalsIgnoreCase("F"); // M이면 false, F면 true

            System.out.print("  ▶ 나를 표현하는 한 줄 소개: ");
            String introduction = sc.nextLine();

            System.out.println("\n  [ 시스템 처리 중... ]");

            SignupRequest request = new SignupRequest(
                    username,
                    password,
                    email,
                    name,
                    nickname,
                    phoneNumber,
                    address,
                    isFemale,
                    introduction
            );

            SignupResponse response = userController.signupProcess(request);

            userOutputView.displaySignupSuccess(response);

        } catch (Exception e) {
            userOutputView.displaySignupFailure(e.getMessage());
        }




    }

    // 마이페이지 디스플레이
    public void displayMyPageMenu() {
        System.out.println("\n────────────────────────────────────────────────────────────────");
        System.out.println("  🚶 마을 길을 따라 나의 숙소로 이동합니다...");

        // 1. 역동적인 가로 보행 모션 (애니메이션)
        // 💡 10년 차 선배의 팁: '\r'과 System.out.flush()를 활용하여
        // 한 줄 안에서 프레임이 바뀌는 효과를 줍니다.
        try {
            int totalSteps = 25; // 걷는 전체 거리 (칸 수)
            int animSpeed = 150; // 걸음 속도 (밀리초, 낮을수록 빠름)

            for (int i = 0; i < totalSteps; i++) {
                // 프레임 구성을 위한 빌더
                StringBuilder frame = new StringBuilder("\r"); // 커서를 줄 맨 앞으로 이동

                // i번만큼 공백을 채워 발자국 위치를 오른쪽으로 미룸
                for (int j = 0; j < i; j++) {
                    frame.append(" ");
                }

                // 🌟 발자국 아스키 아트 (옆으로 걸어가는 모양)
                // 짝수/홀수 프레임에 따라 미묘하게 모양을 바꾸면 더 걷는 느낌이 납니다!
                if (i % 2 == 0) {
                    frame.append("👣 (뚜벅)");
                } else {
                    frame.append("  👣 (뚜벅)");
                }

                // 만든 프레임을 출력 (ln 아님에 주의!)
                System.out.print(frame.toString());

                // 🌟 버퍼를 강제로 비워 즉시 화면에 출력되게 합니다.
                System.out.flush();

                Thread.sleep(animSpeed);
            }
            // 도착 후 다음 줄로 넘어감
            System.out.println("\n\n  🏠 [도착!] 정수님의 숙소 앞입니다.");
            System.out.println("────────────────────────────────────────────────────────────────\n");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 숙소 내부 모션
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    🏡 나의 편안한 숙소                         ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("      [ 1 ] 내 정보 조회 (거울 보기)");
            System.out.println("      [ 2 ] 내 정보 수정 (옷장 정리)");
            System.out.println("      [ 3 ] 내 학습 지표 보기 (현재 공사 중 🚧)");
            System.out.println("      [ 0 ] 메인페이지로 돌아가기 (숙소 나서기)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 행동의 번호를 입력해주세요 : ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    // TODO: 내 정보 조회 로직
                    showProfileProcess();
                    System.out.println("\n  [ 시스템 ] 거울을 봅니다. (내 정보 조회 실행)");
                    break;
                case "2":
                    // TODO: 내 정보 수정 로직
                    displayEditInfoMenu();
                    System.out.println("\n  [ 시스템 ] 옷장을 정리합니다. (내 정보 수정 실행)");
                    break;
                case "3":
                    System.out.println("\n  🚧 아직 공사 중인 공간입니다. (학습 지표 기능 구현 예정)");
                    break;
                case "0":
                    System.out.println("\n  🚪 문을 열고 다시 활기찬 마을(메인페이지)로 나섭니다...");
                    // 🌟 return을 사용하면 이 메서드가 종료되고,
                    // 나를 호출했던 이전 루프(메인페이지 루프)로 자연스럽게 돌아갑니다!
                    return;
                default:
                    System.out.println("\n  [🚨] 올바른 번호(0, 1, 2, 3)를 입력해주세요.");
            }
        }
    }

    private void displayEditInfoMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    👗 옷장 정리 (정보 수정)                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("      [ 1 ] 닉네임 변경 (새로운 명찰 달기)");
            System.out.println("      [ 2 ] 이메일 변경 (연락처 갱신)");
            System.out.println("      [ 3 ] 비밀번호 변경 (자물쇠 교체)");
            System.out.println("      [ 4 ] 이전으로 (옷장 닫기)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 행동의 번호를 입력해주세요 : ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 새로운 명찰을 준비합니다.");
                    updateNicknameProcess();
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] 새로운 연락처를 수첩에 적습니다.");
                    updateEmailProcess();
                    break;
                case "3":
                    System.out.println("\n  [ 시스템 ] 안전을 위해 새로운 자물쇠로 교체합니다.");
                    updatePasswordProcess();
                    break;
                case "4":
                    System.out.println("\n  🚪 옷장 문을 닫고 다시 방으로 돌아갑니다.");
                    // 🌟 return을 호출하면 이 메서드가 종료되고, 이전 화면인 '숙소 메인 메뉴'로 돌아갑니다.
                    return;
                default:
                    System.out.println("\n  [🚨] 올바른 번호(1, 2, 3, 4)를 입력해주세요.");
            }
        }
    }

    private void updatePasswordProcess() {
        userController.updatePasswordProcess();
    }

    private void updateEmailProcess() {

        try {
            System.out.println("\n  [ 시스템 ] 새로운 연락처를 적을 수첩을 준비합니다.");
            System.out.print("  ▶ 새롭게 적고 싶은 이메일을 입력하세요: ");
            String newEmail = sc.nextLine();

           MyPageUpdateResponse response =userController.updateEmailProcess(newEmail);

           userOutputView.displayUpdateSuccess(response);
        } catch (Exception e) {
            // [❌개발 중 삭제 금지] 병합 중에도 추가하기, 디버깅용
            e.printStackTrace();
            userOutputView.displayUpdateFailure(e.getMessage());
        }
    }

    private void updateNicknameProcess() {
        try {
            System.out.println("\n  [ 시스템 ] 새로운 명찰을 준비합니다.");
            System.out.print("  ▶ 새롭게 달고 싶은 닉네임(명찰)을 입력하세요: ");
            String newNickname = sc.nextLine();

            MyPageUpdateResponse response = userController.updateNicknameProcess(newNickname);

            userOutputView.displayUpdateSuccess(response);
        } catch (Exception e) {
            // [❌개발 중 삭제 금지] 병합 중에도 추가하기, 디버깅용
            e.printStackTrace();
            userOutputView.displayUpdateFailure(e.getMessage());
        }
    }


    private void showProfileProcess() {
        try {
            MyPageResponse response =userController.findById();

            userOutputView.displayMyPageSuccess(response);

        } catch (Exception e) {
            // [❌개발 중 삭제 금지] 병합 중에도 추가하기, 디버깅용
            e.printStackTrace();
            userOutputView.displayMyPageFailure(e.getMessage());
        }
    }

}
