package com.lms.domain.village.view;

import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.domain.village.dto.response.CreateVillageResponse;
import com.lms.global.util.InviteCodeUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VillageInputView {

    private final VillageOutputView outputView;
    private final VillageController villageController;
    private final Scanner sc = new Scanner(System.in);

    public VillageInputView(VillageOutputView outputView,
                            SectionInputView sectionInputView, VillageController villageController) {
        this.outputView = outputView;
        this.villageController = villageController;
    }
    // 마을 생성 기능 (관리자만 가능)
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
        // TODO : VillageController 호출하기부터 시작
        try {
            CreateVillageResponse response = villageController.createVillageProcess(request);
            outputView.displayInsertSuccess(response);

        } catch (Exception e) {
            outputView.displayFalse(e.getMessage());
        }
    }

    // TODO : 삭제해야될 코드
    private void enterFixedVillage() {
        VillageDTO village = new VillageDTO();
        village.setVillageId(1L);
        village.setVillageName("1번 마을");

        outputView.printVillageEnterSuccess(village);
        showVillageMainMenu(village);
    }




    // TODO : 강사 메인 메뉴 (관리자도 강사랑 똑같이)
    public void showstudentMainMenu(VillageDTO village) {
        while (true) {
            System.out.println("\n===> " + village.getVillageName() + " 메인페이지 입장");
            System.out.println("1. 출석 체크");
            System.out.println("2. 교육센터로 이동");
            System.out.println("3. 게시판으로 이동");
            System.out.println("4. 숙소(마이페이지)로 이동");
            System.out.println("5. 마피아 게시판으로 이동");
            System.out.println("6. 로그아웃");
            System.out.print("번호 입력 : ");

            int menu;
            try {

                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            switch (menu) {
                case 1:
                    System.out.println("출석 체크 기능은 준비 중입니다.");
                    break;
                case 2:
                    sectionInputView.displayStudentSectionMenu(village);
                    break;
                case 3:
                    System.out.println("게시판 기능은 준비 중입니다.");
                    break;
                case 4:
                    System.out.println("마이페이지 기능은 준비 중입니다.");
                    break;
                case 5:
                    System.out.println("마피아 게시판 기능은 준비 중입니다.");
                    break;
                case 6:
                    System.out.println("로그아웃합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 학생 메인 메뉴, TODO : VillageDTO village 지우고 stay 테이블에서 넘어온 값 받기
    public void showVillageMainMenu(VillageDTO village) {
        while (true) {
            System.out.println("\n===> " + village.getVillageName() + " 메인페이지 입장");
            System.out.println("1. 출석 체크");
            System.out.println("2. 교육센터로 이동");
            System.out.println("3. 게시판으로 이동");
            System.out.println("4. 숙소(마이페이지)로 이동");
            System.out.println("5. 마피아 게시판으로 이동");
            System.out.println("6. 로그아웃");
            System.out.print("번호 입력 : ");

            int menu;
            try {
                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            switch (menu) {
                case 1:
                    System.out.println("출석 체크 기능은 준비 중입니다.");
                    break;
                case 2:
                    sectionInputView.displayInstructorSectionMenu(village.getVillageId());
                    break;
                case 3:
                    System.out.println("게시판 기능은 준비 중입니다.");
                    break;
                case 4:
                    System.out.println("마이페이지 기능은 준비 중입니다.");
                    break;
                case 5:
                    System.out.println("마피아 게시판 기능은 준비 중입니다.");
                    break;
                case 6:
                    System.out.println("로그아웃합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
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
