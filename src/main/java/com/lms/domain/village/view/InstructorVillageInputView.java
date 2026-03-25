package com.lms.domain.village.view;

import com.lms.domain.users.dto.UserRole;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.domain.village.dto.response.CreateVillageResponse;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;
import com.lms.global.util.InviteCodeUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InstructorVillageInputView {

    private final VillageController villageController;
    private final VillageOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public InstructorVillageInputView(VillageController villageController, VillageOutputView outputView) {
        this.villageController = villageController;
        this.outputView = outputView;
    }

    // comment. 강사 메인 메뉴
    public void displayInstructorMainMenu(long villageId) {

        long currentUser = UserSession.getLoggedInUser().getUserId();
        UserRole currentUserRole = UserSession.getLoggedInUser().getRole();

        while (true) {

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                👑 마을 메인 광장 (강사 전용 모드)              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 강사님, 환영합니다! 마을의 전반적인 관리를 시작합니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📅 출석 체크 및 관리");
            System.out.println("      [ 2 ] 🎓 교육센터 관리 (강의 업로드 및 현황)");
            System.out.println("      [ 3 ] 📢 마을 게시판 관리");
            System.out.println("      [ 4 ] 🕵️‍♂️ 마피아 게시판 관리");
            System.out.println("      [ 5 ] 🏠 강사 숙소 (마이페이지)");
            System.out.println("      [ 6 ] 👥 수강생 관리 (입장 승인 및 추방)");
            System.out.println("      [ 7 ] 🎲 오늘의 마피아 지목하기");
            System.out.println("      [ 0 ] 🚪 로그인 화면 (마을 떠나기)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 메뉴의 번호를 입력해주세요 : ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    // TODO : 연동 OK, 테스트 해야함
                    System.out.println("\n  [ 시스템 ] 📅 출석 관리 시스템으로 이동합니다...");
                    AppContext.getAppContext().attendAppContext.attendInputView.AttendMenu(villageId);
                    break;

                case "2":
                    System.out.println("\n  [ 시스템 ] 🎓 교육센터 관리 시스템으로 이동합니다...");
                    // comment, 연동 OK
                    AppContext.getAppContext().sectionAppContext.instructorSectionInputView
                            .displayInstructorSectionMenu(villageId);
                    break;

                case "3":
                    System.out.println("\n  [ 시스템 ] 📢 마을 게시판 관리로 이동합니다...");
                    // TODO : 연동 OK, 테스트 해야함
                    AppContext.getAppContext().boardAppContext.boardInputView.boardFirstMenu(villageId);
                    break;

                case "4":
                    System.out.println("\n  [ 시스템 ] 🕵️‍♂️ 마피아 게시판 관리로 이동합니다...");
                    AppContext.getAppContext().quizAppContext.quizInputView.displayMainMenu(villageId);
                    break;

                case "5":
                    System.out.println("\n  [ 시스템 ] 🏠 강사 숙소(마이페이지)로 이동합니다...");
                    // comment, 연동 OK
                    AppContext.getAppContext().userAppContext.userInputView.displayMyPageMenu();
                    break;

                case "6":
                    System.out.println("\n  [ 시스템 ] 👥 수강생 관리 시스템으로 이동합니다...");
                    // comment, 연동 OK
                    AppContext.getAppContext().enrollmentAppContext
                            .instructorEnrollmentInputView.displayInstructorEnrollmentMenu(villageId);
                    break;

                case "7":
                    // TODO : 연동 OK, 테스트 해야함
                    System.out.println("\n  [ 시스템 ] 🎲 오늘의 마피아 지목 시스템을 가동합니다...");
                    AppContext.getAppContext().mafiaAppContext.mafiaInputView.selectMafia(villageId);
                    break;

                case "0":
                    System.out.println("\n  [ 시스템 ] 🚪 메인 화면으로 돌아갑니다.");
                    if (currentUserRole == UserRole.ADMIN) {
                        AppContext.getAppContext().cityAppContext.cityInputView.displayCityAdminMenu();
                        return;
                    }
                    if (currentUserRole == UserRole.INSTRUCTOR) {
                        AppContext.getAppContext().enrollmentAppContext.enrollmentInputView.displayEnrollMainMenu();
                        return;
                    }

                default:
                    System.out.println("\n  🚨 [오류] 올바른 메뉴 번호(0~7)를 입력해 주세요.");
            }
        }
    }


}
