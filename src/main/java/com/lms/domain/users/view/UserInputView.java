package com.lms.domain.users.view;

import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.response.LoginResponse;

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
                    processLogin();
                    break;
                case "2":
                    processSignup();
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

    private void processLogin() {
        System.out.println("\n[ 🔐 로그인 ]");
        System.out.print("  ▶ 아이디: ");
        String username = sc.nextLine();

        System.out.print("  ▶ 비밀번호: ");
        String password = sc.nextLine();

        try {
            LoginResponse response = userController.loginProcess(username, password);

            userOutputView.displayLoginSuccess(response);

            // TODO: response.getRole()을 확인 후 강사 메뉴 / 학생 메뉴로 이동하는 로직이 추가!


        } catch (SQLException e) {
            userOutputView.displayLoginFailure(e.getMessage());
        }
    }

    private void processSignup() {

    }

}
