package com.lms.domain.enrollment.view;

import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.enrollment.dto.Response.EnterVillageResponse;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;

import java.util.List;
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
                    enterVillageProcess();
                    // TODO: 승인 여부 체크 후 마을 진입 라우팅
                    // TODO : 승인된 마을 전체 조회 해서 해당 villageId 받아서 전달하기
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

    // 초대코드 입력 기능
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

    // 3번 입장하기 로직
    private void enterVillageProcess() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🚪 마을 광장으로 입장 (Enter Village)           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 여행자님의 입국이 허가된 마을 목록을 조회합니다...\n");

        try {
            long currentUserId = UserSession.getLoggedInUser().getUserId();

            // 1. [조회] 현재 학생이 가입 '승인(APPROVED)'된 마을 목록만 가져옵니다.
            List<EnterVillageResponse> approvedVillages = controller.getApprovedVillages(currentUserId);

            // 방어 로직: 들어갈 수 있는 마을이 없는 경우
            if (approvedVillages == null || approvedVillages.isEmpty()) {
                System.out.println("  [ 시스템 ] 현재 입장 가능한(승인 완료된) 마을이 없습니다.");
                System.out.println("  [ 시스템 ] '마을 가입 신청'을 먼저 진행하시거나 강사님의 승인을 기다려주세요.");
                System.out.println("────────────────────────────────────────────────────────────────\n");
                return;
            }

            // OutputView를 통해 승인된 마을 목록을 예쁘게 출력합니다.
            outputView.displayApprovedVillages(approvedVillages);

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 입장하실 마을의 번호(ID)를 입력하세요 (취소는 '0') : ");

            long villageId;
            try {
                villageId = Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n  🚨 [오류] 마을 번호는 숫자만 입력 가능합니다.");
                return;
            }

            if (villageId == 0) {
                System.out.println("\n  [ 시스템 ] 마을 입장을 취소하고 수강 관리 메뉴로 돌아갑니다.");
                return;
            }

            // TODO : 사용자가 입력한 villageId가 진짜로 승인된 마을이 맞는지 Controller/Service에서 2차 검증합니다.
            boolean isApproved = controller.verifyVillageApproval(currentUserId, villageId);

            if (!isApproved) {
                System.out.println("\n  🚨 [보안 경고] 해당 마을에 입장할 권한이 없거나 아직 승인 대기 중입니다.");
                return;
            }

            System.out.println("\n  [ 시스템 ] 짐을 챙기세요! " + villageId + "번 마을로 안전하게 이동합니다... 🚀");
            // 역할에 따른 메뉴 분기
            if (UserSession.getLoggedInUser().getRole() == UserRole.STUDENT) {
                AppContext.getAppContext().villageAppContext.villageInputView.displayStudentMainMenu(villageId);
            } else if (UserSession.getLoggedInUser().getRole() == UserRole.INSTRUCTOR
                    || UserSession.getLoggedInUser().getRole() == UserRole.ADMIN) {
                AppContext.getAppContext().villageAppContext.villageInputView.displayInstructorMainMenu(villageId);
            } else {
                System.out.println("  🚨 [오류] 정의되지 않은 사용자 권한입니다.");
            }
        } catch (Exception e) {
            System.out.println("\n  🚨 [오류] 마을 입장 처리 중 문제가 발생했습니다: " + e.getMessage());
        }
    }

    // ===== 강사용 수강생 관리 메뉴 추가 =====

    public void displayInstructorEnrollmentMenu(long villageId) {
        while (true) {
            System.out.println("\n수강생 관리 (강사 특권)");
            System.out.println("1. 수강 신청 승인 및 거절");
            System.out.println("2. 수강생 강제 추방");
            System.out.println("3. 대기 학생 강사 승격");
            System.out.println("4. 이전으로");
            System.out.print("번호 입력 : ");

            String input = sc.nextLine();

            switch (input) {
                case "1":
                    approveOrRejectEnrollment(villageId);
                    break;
                case "2":
                    expelEnrollment(villageId);
                    break;
                case "3":
                    promoteStudentToInstructor(villageId);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("잘못된 번호입니다.");
            }
        }
    }

    private void promoteStudentToInstructor(long villageId) {
        try {
            System.out.println("=== 대기 중인 수강 신청 목록을 조회합니다. ===");
            java.util.List<java.util.Map<String, Object>> waitingList =
                    controller.findWaitingEnrollmentList(villageId);

            if (waitingList.isEmpty()) {
                System.out.println("대기 중인 수강 신청이 없습니다.");
                return;
            }

            outputView.printEnrollmentList(waitingList);

            System.out.print("- 강사로 승격할 수강 신청 번호(enrollment_id)를 입력해주세요 : ");
            long enrollmentId = Long.parseLong(sc.nextLine());

            java.util.Map<String, Object> target =
                    controller.findEnrollmentManageTarget(villageId, enrollmentId);

            if (target == null) {
                System.out.println("존재하지 않는 신청 번호입니다.");
                return;
            }

            String status = String.valueOf(target.get("status"));
            if (!"WAITING".equalsIgnoreCase(status)) {
                System.out.println("대기 중인 신청만 강사로 승격할 수 있습니다.");
                return;
            }

            System.out.print("- 정말로 " + target.get("userName")
                    + " 님을 강사(INSTRUCTOR)로 승격하시겠습니까? (Y/N) : ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                controller.promoteWaitingStudentToInstructor(enrollmentId);
                System.out.println("=== 해당 학생이 강사(INSTRUCTOR)로 승격되었습니다. ===");
            } else if (confirm.equalsIgnoreCase("N")) {
                System.out.println("=== 강사 승격이 취소되었습니다. ===");
            } else {
                System.out.println("잘못된 입력입니다.");
            }

        } catch (NumberFormatException e) {
            System.out.println("신청 번호는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void approveOrRejectEnrollment(long villageId) {
        try {
            System.out.println("=== 대기 중인 수강 신청 목록을 조회합니다. ===");
            java.util.List<java.util.Map<String, Object>> waitingList = controller.findWaitingEnrollmentList(villageId);

            if (waitingList.isEmpty()) {
                System.out.println("대기 중인 수강 신청이 없습니다.");
                return;
            }

            outputView.printEnrollmentList(waitingList);

            System.out.print("- 심사할 수강 신청 번호(enrollment_id)를 입력해주세요 : ");
            long enrollmentId = Long.parseLong(sc.nextLine());

            java.util.Map<String, Object> target = controller.findEnrollmentManageTarget(villageId, enrollmentId);

            if (target == null) {
                System.out.println("존재하지 않는 신청 번호입니다.");
                return;
            }

            System.out.println("=== " + target.get("userName") + " 님의 가입을 승인하시겠습니까? (1: 승인, 2: 거절) ===");
            System.out.print("- 입력 : ");
            String choice = sc.nextLine();

            if ("1".equals(choice)) {
                controller.approveEnrollment(villageId, enrollmentId);
                System.out.println("=== 승인 완료. 해당 학생이 마을 주민이 되었습니다. ===");
            } else if ("2".equals(choice)) {
                controller.rejectEnrollment(villageId, enrollmentId);
                System.out.println("=== 가입이 거절되었습니다. ===");
            } else {
                System.out.println("잘못된 입력입니다.");
            }

        } catch (NumberFormatException e) {
            System.out.println("신청 번호는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void expelEnrollment(long villageId) {
        try {
            System.out.println("=== 현재 마을의 승인된 전체 수강생 목록을 조회합니다. ===");
            java.util.List<java.util.Map<String, Object>> approvedList = controller.findApprovedEnrollmentList(villageId);

            if (approvedList.isEmpty()) {
                System.out.println("승인된 수강생이 없습니다.");
                return;
            }

            outputView.printEnrollmentList(approvedList);

            System.out.print("- 추방할 학생의 수강 번호(enrollment_id)를 입력해주세요 : ");
            long enrollmentId = Long.parseLong(sc.nextLine());

            java.util.Map<String, Object> target = controller.findEnrollmentManageTarget(villageId, enrollmentId);

            if (target == null) {
                System.out.println("존재하지 않는 신청 번호입니다.");
                return;
            }

            System.out.print("- 정말로 " + target.get("userName") + " 님을 이 마을에서 강제 추방하시겠습니까? (Y/N) : ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                controller.expelEnrollment(villageId, enrollmentId);
                System.out.println("=== 해당 학생이 추방 처리되어 더 이상 마을에 접근할 수 없습니다. ===");
            } else if (confirm.equalsIgnoreCase("N")) {
                System.out.println("=== 추방 처리가 취소되었습니다. ===");
            } else {
                System.out.println("잘못된 입력입니다.");
            }

        } catch (NumberFormatException e) {
            System.out.println("신청 번호는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        }
    }
}
