package com.lms.domain.village.view;

import com.lms.domain.village.controller.VillageController;
import com.lms.global.AppContext.AppContext;

import java.util.Scanner;

public class StudentVillageInputView {

    private final VillageController villageController;
    private final VillageOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public StudentVillageInputView(VillageController villageController, VillageOutputView outputView) {
        this.villageController = villageController;
        this.outputView = outputView;
    }

    // comment. 학생 메인 메뉴
    public void displayStudentMainMenu(Long villageId) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🏡 마을 광장 (학생 전용)                       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            // TODO : 전달 받은 viilageId로 findById() 이용해서 villageName 받아오기
//            System.out.println("  [ 소속 마을 ] " + villageName);
            System.out.println("  [ 시스템 ] 환영합니다! 마을의 다양한 교육 인프라를 이용해 보세요.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📅 출석 체크 (출입 기록)");
            System.out.println("      [ 2 ] 🎓 교육센터 입장 (섹션 및 강의 수강)");
            System.out.println("      [ 3 ] 📢 마을 게시판 (공지 및 소통)");
            System.out.println("      [ 4 ] 🏠 개인 숙소 (마이페이지)");
            System.out.println("      [ 5 ] 🕵️ 마피아 광장 (휴식 및 미니게임)");
            System.out.println("      [ 0 ] 🚪 마을 퇴장 (로그아웃 / 이전 화면으로 이동)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 시설의 번호를 입력해주세요 : ");

            // 🌟 예외 처리 없이 String으로 깔끔하게 처리
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // comment, 연동 OK, 테스트 해야함
                    System.out.println("\n  [ 시스템 ] 📅 출석 체크 방으로 아동합니다...");
                    AppContext.getAppContext().attendAppContext.attendInputView.AttendMenu(villageId);
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] 🎓 교육센터(Section)로 이동합니다...");
                    // comment, 연동 OK
                    AppContext.getAppContext().sectionAppContext.studentSectionInputView.displayStudentSectionMenu(villageId);
                    break;
                case "3":
                    // comment, 연동 OK, 테스트 해야함
                    System.out.println("\n  [ 시스템 ] 📢 마을 게시판으로 아동합니다...");
                    AppContext.getAppContext().boardAppContext.boardInputView.boardFirstMenu(villageId);
                    break;
                case "4":
                    System.out.println("\n  [ 시스템 ] 🏠 개인 숙소(마이페이지)으로 아동합니다...");
                    // comment, 연동 OK
                    AppContext.getAppContext().userAppContext.userInputView.displayMyPageMenu();
                    break;
                case "5":
                    System.out.println("\n  [ 시스템 ] 🕵️ 마피아 광장으로 아동합니다...");
                    AppContext.getAppContext().quizAppContext.quizInputView.displayMainMenu(villageId);
                    break;
                case "0":
                    System.out.println("\n  [ 시스템 ] 마을에서 퇴장합니다. 짐을 챙겨 이동합니다...");
                    return;
                default:
                    System.out.println("\n  🚨 [오류] 올바른 시설 번호(0~5)를 입력해주세요.");
            }
        }
    }
}
