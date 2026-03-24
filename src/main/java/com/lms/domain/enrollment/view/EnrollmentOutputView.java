package com.lms.domain.enrollment.view;

import com.lms.domain.enrollment.dto.Response.ApprovedEnrollmentResponse;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.WaitingEnrollmentResponse;

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

    public void displayApprovedEnrollments(List<ApprovedEnrollmentResponse> list) {
        System.out.println("\n  [ 🏡 마을 주민 목록 (승인 완료) ]");
        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.println("  [ 수강번호 ] |    [ 성함 ]    |  [ 상태 ]  |      [ 최종 업데이트 ]");
        System.out.println("────────────────────────────────────────────────────────────────");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (list.isEmpty()) {
            System.out.println("                현재 마을에 승인된 수강생이 없습니다.           ");
        } else {
            for (ApprovedEnrollmentResponse response : list) {
                String dateStr = response.getEnrolledAt() != null
                        ? response.getEnrolledAt().format(formatter)
                        : "기록 없음";

                System.out.printf("   No.%-7d | %-12s | %-8s | %s\n",
                        response.getEnrollmentId(),
                        response.getName(),
                        response.getStatus(),
                        dateStr
                );
            }
        }
        System.out.println("────────────────────────────────────────────────────────────────");
    }

    public void displayWaitingEnrollments(List<WaitingEnrollmentResponse> list) {
        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.println("  [신청 번호] |    [ 마을 이름 ]    | [ 신청자명 ] | [ 신청 일시 ]");
        System.out.println("────────────────────────────────────────────────────────────────");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (WaitingEnrollmentResponse response : list) {


            String formattedDate = "날짜 정보 없음";
            if (response.getAppliedAt() != null) {
                formattedDate = response.getAppliedAt().format(formatter);
            }

            System.out.printf("   ▶ No.%-3d | %-13s | %-8s | %s\n",
                    response.getEnrollmentId(),
                    response.getVillageName(),
                    response.getName(),
                    formattedDate
            );
        }
        System.out.println("────────────────────────────────────────────────────────────────");
    }
}
