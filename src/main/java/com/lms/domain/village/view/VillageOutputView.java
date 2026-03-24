package com.lms.domain.village.view;

import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.response.CreateVillageResponse;

public class VillageOutputView {

    public void printError(String message) {
        System.out.println("[오류] " + message);
    }

    public void printVillageEnterSuccess(VillageDTO village) {
        System.out.println("\n마을 입장 성공");
        System.out.println("마을명: " + village.getVillageName());
    }

    public void displayFalse(String errorMessage) {
        System.out.println("\n🚨 건축 실패: " + errorMessage + "\n");
    }

    public void displayInsertSuccess(CreateVillageResponse response) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🏕️ 신규 마을 개척 성공 보고서                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 성공적으로 새로운 교육 커뮤니티 마을이 개척되었습니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        // Response DTO에서 ID와 마을 이름을 꺼내어 출력합니다.
        System.out.printf("   ▶ 발급된 마을 식별 코드(ID) : %d\n", response.getVillageId());
        System.out.printf("   ▶ 개척된 마을명             : %s\n", response.getVillageName());
        System.out.printf("   ▶ 개척된 마을 입장 코드        : %s\n", response.getInviteCode());

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.println("  [ 시스템 ] 이전 메뉴로 돌아갑니다...\n");
    }
}
