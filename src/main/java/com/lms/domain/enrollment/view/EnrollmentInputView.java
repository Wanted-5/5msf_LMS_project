package com.lms.domain.enrollment.view;

import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;

import java.util.Scanner;

public class EnrollmentInputView {

    private final EnrollmentController controller;
    private final EnrollmentOutputView outputView;

    Scanner sc = new Scanner(System.in);

    public EnrollmentInputView(EnrollmentController controller, EnrollmentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayEnrollMainMenu() {

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🏫 대기실 로비 (마을 입장 및 수강 신청)           ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 환영합니다! 아직 소속된 마을이 없거나 승인 대기 중입니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 마을 입장 코드(초대 코드) 입력 및 신청");
            System.out.println("      [ 2 ] 내 입장(신청) 상태 조회");
            System.out.println("      [ 3 ] 마을 입장하기 (관리자 승인 완료 시 가능)");
            System.out.println("      [ 0 ] 로그아웃 (초기 화면으로 이동)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 메뉴의 번호를 입력해주세요 : ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 마을 입장 코드 검증 프로세스를 가동합니다...");
                    // TODO: 초대 코드 입력 및 검증 메서드 호출 (예: submitEnrollmentCodeProcess())
                    submitEnrollmentCodeProcess();
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] 현재 수강 신청 및 승인 상태를 조회합니다...");
                    // TODO: 상태 조회 메서드 호출 (예: checkEnrollmentStatusProcess())
                    break;
                case "3":
                    System.out.println("\n  [ 시스템 ] 승인된 마을로 입장을 시도합니다...");
                    // TODO: 승인 여부 체크 후 마을 진입 라우팅
                    // TODO : 승인된 마을 전체 조회 해서 해당 villageId 받아서 전달하기
//                    AppContext.getAppContext().villageAppContext.villageInputView.displayStudentMainMenu(villageId);
                    break;
                case "0":
                    System.out.println("  [ 시스템 ] 안전하게 로그아웃 되었습니다. 초기 화면으로 돌아갑니다.");
                    UserSession.setLoggedInUser(null);
                    return; // while 루프를 빠져나가 이전 화면(메인 로그인 화면)으로 복귀
                default:
                    System.out.println("\n  🚨 [오류] 올바른 메뉴 번호(0~3)를 입력해주세요.");

            }

        }
    }

    // 초대코드 입력 display
    private void submitEnrollmentCodeProcess() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🔑 마을 입장 코드(초대 코드) 검증                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 강사님이나 관리자에게 부여받은 초대 코드를 입력해 주세요.");
            System.out.println("  [ 시스템 ] (입력을 취소하고 로비로 돌아가려면 '0'을 입력하세요)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 입장 코드 입력 : ");

            String inviteCode = sc.nextLine().trim();

            // 1. 취소 옵션
            if (inviteCode.equals("0")) {
                System.out.println("\n  [ 시스템 ] 코드 입력을 취소하고 대기실 로비로 돌아갑니다.");
                return;
            }

            try {
                VerifyInviteCodeResponse targetVillage = controller.verifyInviteCode(inviteCode);

                System.out.println("\n  [ 시스템 ] 코드가 확인되었습니다! 아래 마을 정보를 확인해 주세요.");
                System.out.println("────────────────────────────────────────────────────────────────");
                 System.out.printf("   ▶ 마을 이름 : %s\n", targetVillage.getVillageName());
                 System.out.printf("   ▶ 상세 설명 : %s\n", targetVillage.getDescription());
                 System.out.printf("   ▶ 시작 일자 : %s\n", targetVillage.getStartDate());
                System.out.println("────────────────────────────────────────────────────────────────");

                System.out.println("  🚨 [확인] 위 마을로 수강 신청(입장 대기)을 진행하시겠습니까?");
                System.out.print("  ▶ 동의하시면 대문자 'Y'를 입력해 주세요 (취소는 아무 키) : ");

                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    // 🌟 4. 실제 신청 로직 실행 (DB의 enrollment 테이블에 INSERT)
                    // 현재 로그인한 유저의 ID를 가져와서 신청합니다.
                     long currentUserId = UserSession.getLoggedInUser().getUserId();
                     controller.submitEnrollment(currentUserId, targetVillage.getVillageId());

                    System.out.println("\n  🎉 [ 시스템 ] 수강 신청이 완료되었습니다! 관리자 승인을 기다려주세요.");
                    return;
                } else {
                    System.out.println("\n  [ 시스템 ] 신청이 취소되었습니다. 다시 코드를 입력해주세요.");
                }

            } catch (Exception e) {
                // 개발 단게 원인 분석
                e.printStackTrace();
                System.out.println("\n  🚨 [오류] " + e.getMessage());
                System.out.println("  [ 시스템 ] 코드를 다시 확인하고 입력해 주세요.");
            }

            }
    }
}
