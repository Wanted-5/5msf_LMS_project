package com.lms.domain.enrollment.view;

import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EnrollmentOutputView {
        public void displayApprovedVillages(List<EnterVillageResponse> approvedVillages) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🏰 입장 가능한 마을(Village) 목록               ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            // 실무 팁: DB에서 가져온 날짜/시간 데이터는 항상 View 계층에서 사용자가 보기 편하게 포맷팅해 줍니다.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            // 리스트에서 마을 정보를 하나씩 꺼내어 예쁘게 배치합니다.
            for (EnterVillageResponse village : approvedVillages) {
                String formattedDate = village.getAppliedAt().format(formatter);

                System.out.printf("  ▶ [ 마을 번호: %2d ] %s\n", village.getVillageId(), village.getVillageName());
                System.out.printf("      - 입장 권한 : ✅ %s\n", village.getStatus());
                System.out.printf("      - 승인 일시 : %s\n", formattedDate);
                System.out.println("────────────────────────────────────────────────────────────────");
            }
        }
}
