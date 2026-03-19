package com.lms.domain.users.view;

import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.request.SignupRequest;
import com.lms.domain.users.dto.response.LoginResponse;
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
//이자리에 메서드 넣어 테스트
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

}
