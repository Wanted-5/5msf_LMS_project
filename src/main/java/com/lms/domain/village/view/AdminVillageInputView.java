package com.lms.domain.village.view;

import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.domain.village.dto.response.CreateVillageResponse;
import com.lms.global.util.InviteCodeUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AdminVillageInputView {

    private final VillageController villageController;
    private final VillageOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public AdminVillageInputView(VillageController villageController, VillageOutputView outputView) {
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
