package com.lms.domain.enrollment.view;

import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


public class EnrollmentOutputView {

        public void displayFailure(String errorMessage) {
            System.out.println("\n🚨 건설 실패: " + errorMessage + "\n");
        }

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

    public void displayWatingVillages(List<EnterVillageResponse> waitingVillages) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 📜 마을 가입 신청 내역 조회                      ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (EnterVillageResponse village : waitingVillages) {
                String formattedDate = village.getAppliedAt().format(formatter);

                // [ 실무 UX 디테일 ] 영문 Enum 값을 사용자 친화적인 한글과 이모지로 변환합니다.
                String statusDisplay;
                switch (village.getStatus().name()) {
                    case "WAITING":
                        statusDisplay = "⏳ 승인 대기 중";
                        break;
                    case "APPROVED":
                        statusDisplay = "✅ 입장 승인됨";
                        break;
                    case "REJECTED":
                        statusDisplay = "❌ 가입 거절됨";
                        break;
                    default:
                        statusDisplay = village.getStatus().name();
                }

                System.out.printf("  ▶ [ 마을 번호: %2d ] %s\n", village.getVillageId(), village.getVillageName());
                System.out.printf("      - 진행 상태 : %s\n", statusDisplay);
                System.out.printf("      - 신청 일시 : %s\n", formattedDate);
                System.out.println("────────────────────────────────────────────────────────────────");
            }
    }

    public void printEnrollmentList(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("조회된 대상이 없습니다.");
            return;
        }

        for (Map<String, Object> row : list) {
            System.out.println("--------------------------------");
            System.out.println("신청번호(enrollment_id): " + row.get("enrollmentId"));
            System.out.println("학생번호(user_id): " + row.get("userId"));
            System.out.println("학생이름: " + row.get("userName"));
            System.out.println("상태: " + row.get("status"));
            System.out.println("신청일시: " + row.get("appliedAt"));
        }
        System.out.println("--------------------------------");
    }
}
