package com.lms.domain.village.view;

import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.domain.village.dto.response.CreateVillageResponse;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;
import com.lms.global.util.InviteCodeUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VillageInputView {

    private final VillageController villageController;
    private final VillageOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public VillageInputView(VillageController villageController, VillageOutputView outputView) {
        this.villageController = villageController;
        this.outputView = outputView;
    }


    // comment. [관리자 기능] 마을 생성 기능
    //  강사 메인 메뉴는 CityInputView에 있습니다.
    public void createVillageProcess(Long cityId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🏕️ 신규 마을(Village) 개척 기안                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 선택하신 도시 내에 새로운 교육 커뮤니티를 개척합니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.print("  ▶ 개척할 마을의 이름 (예: 백엔드 트랙 1기) : ");
        String villageName = sc.nextLine();

        System.out.print("  ▶ 마을 상세 설명 (교육 목적 및 특징) : ");
        String description = sc.nextLine();

        LocalDate startDate = parseDateInput("  ▶ 코스 시작일 (YYYY-MM-DD, 미정이면 엔터) : ");
        LocalDate endDate = parseDateInput("  ▶ 코스 종료일 (YYYY-MM-DD, 미정이면 엔터) : ");

        String inviteCode = InviteCodeUtil.generateVillageInviteCode(cityId);

        System.out.println("\n  [ 시스템 ] 기안서를 바탕으로 마을 개척을 시작합니다...");
        System.out.println("  [ 시스템 ] 발급된 예비 초대 코드 : " + inviteCode);

        CreateVillageRequest request = new CreateVillageRequest(
                cityId,
                villageName,
                description,
                inviteCode,
                startDate,
                endDate
        );
        try {
            CreateVillageResponse response = villageController.createVillageProcess(request);
            outputView.displayInsertSuccess(response);

        } catch (Exception e) {
            outputView.displayFalse(e.getMessage());
        }
    }


    // TODO : 강사 메인 메뉴 구현 해야함
    public void displayInstructorMainMenu(long villageId) {
        while (true) {
            System.out.println("\n===> 4. 마을 메인페이지 입장 (강사 모드)");
            System.out.println("=== 메인 콘솔 (초기 화면 - 강사) ===");
            System.out.println("1. 출석 체크 및 관리");
            System.out.println("2. 교육센터 관리");
            System.out.println("3. 게시판 관리");
            System.out.println("4. 마피아 게시판 관리");
            System.out.println("5. 숙소(마이페이지)로 이동");
            System.out.println("6. 수강생 관리");
            System.out.println("7. 오늘의 마피아 뽑기");
            System.out.println("8. 로그아웃");
            System.out.print("번호 입력 : ");

            String input = sc.nextLine();

            switch (input) {
                case "2":
                    AppContext.getAppContext()
                            .sectionAppContext
                            .sectionInputView
                            .displayInstructorSectionMenu(
                                    villageId,
                                    UserSession.getLoggedInUser().getUserId()
                            );
                    break;

                case "6":
                    AppContext.getAppContext()
                            .enrollmentAppContext
                            .enrollmentInputView
                            .displayInstructorEnrollmentMenu(villageId);
                    break;

                case "8":
                    System.out.println("로그아웃합니다.");
                    UserSession.setLoggedInUser(null);
                    return;

                default:
                    System.out.println("아직 구현되지 않았거나 잘못된 번호입니다.");
            }
        }
    }


    // TODO : 정현이 코드 수정, 학생 메인 메뉴 (관리자도 강사랑 똑같이)
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
                    System.out.println("\n  [ 시스템 ] 📅 출석 체크 기능은 현재 공사 중입니다.");
                    // TODO : 추후 강현이 attendanceInputView와 연결
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] 🎓 교육센터(Section)로 이동합니다...");
                    // TODO: Section 부서로 라우팅
                    AppContext.getAppContext().sectionAppContext.sectionInputView.displayStudentSectionMenu(villageId);
                    break;
                case "3":
                    System.out.println("\n  [ 시스템 ] 📢 마을 게시판 기능은 현재 공사 중입니다.");
                    // TODO : 추후 종호형 boardInputView와 연결
                    break;
                case "4":
                    System.out.println("\n  [ 시스템 ] 🏠 개인 숙소(마이페이지) 기능은 현재 공사 중입니다.");
                    // TODO : 추후 정수 UserInputView와 연결
                    break;
                case "5":
                    System.out.println("\n  [ 시스템 ] 🕵️ 마피아 광장 기능은 현재 공사 중입니다.");
                    // TODO : 추후 태연이 mafiaInputVIew? 와 연결
                    break;
                case "0":
                    System.out.println("\n  [ 시스템 ] 마을에서 퇴장합니다. 짐을 챙겨 이동합니다...");
                    return; // while 루프 종료
                default:
                    System.out.println("\n  🚨 [오류] 올바른 시설 번호(0~5)를 입력해주세요.");
            }
        }
    }

    // ===================== 내부 편의 메서드 ========================
    private LocalDate parseDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return null; // 미정인 경우 null 반환 (DB에 NULL로 들어감)
            }
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("  🚨 [형식 오류] 날짜는 반드시 'YYYY-MM-DD' 형식으로 입력해주세요. (예: 2026-03-01)");
            }
        }
    }
}
