package com.lms.domain.enrollment.view;

import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.enrollment.dto.Response.ApprovedEnrollmentResponse;
import com.lms.domain.enrollment.dto.Response.WaitingEnrollmentResponse;

import java.util.List;
import java.util.Scanner;

public class InstructorEnrollmentInputView {

    private final EnrollmentController controller;
    private final EnrollmentOutputView outputView;

    Scanner sc = new Scanner(System.in);

    public InstructorEnrollmentInputView(EnrollmentController controller, EnrollmentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }


    public void displayInstructorEnrollmentMenu(long villageId) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 👥 수강생 관리 (강사 전용 구역)                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 마을에 입장할 학생을 심사하거나, 기존 수강생을 관리합니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📝 수강 신청 승인 및 거절 (가입 심사)");
            System.out.println("      [ 2 ] 🚨 수강생 강제 추방 (블랙리스트 관리)");
            System.out.println("      [ 0 ] 🔙 이전 메뉴로 돌아가기");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 업무의 번호를 입력해주세요 : ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 📝 수강 신청 심사 화면으로 이동합니다...");
                    approveOrRejectEnrollment(villageId);
                    break;

                case "2":
                    System.out.println("\n  [ 시스템 ] 🚨 수강생 강제 추방 관리 화면으로 이동합니다...");
                    expelEnrollment(villageId);
                    break;

                case "0":
                    System.out.println("\n  [ 시스템 ] 수강생 관리를 종료하고 이전 메뉴로 돌아갑니다.");
                    return;

                default:
                    System.out.println("\n  🚨 [오류] 올바른 메뉴 번호(0~2)를 입력해 주세요.");
            }
        }
    }

    // 수강생 심사 기능
    private void approveOrRejectEnrollment(long villageId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 📝 수강 신청 심사 (승인 및 거절)                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 대기 중인 수강 신청 목록을 불러옵니다...\n");

        try {
            List<WaitingEnrollmentResponse> waitingList = controller.findWaitingEnrollmentList(villageId);

            if (waitingList == null || waitingList.isEmpty()) {
                System.out.println("────────────────────────────────────────────────────────────────");
                System.out.println("  [ 시스템 ] 현재 대기 중인 수강 신청이 없습니다.");
                System.out.println("────────────────────────────────────────────────────────────────\n");
                return;
            }

            outputView.displayWaitingEnrollments(waitingList);

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 심사할 수강생의 '신청 번호(ID)'를 입력해 주세요 (취소는 '0') : ");

            long enrollmentId;
            try {
                enrollmentId = Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n  🚨 [오류] 신청 번호는 숫자만 입력 가능합니다.");
                return;
            }

            if (enrollmentId == 0) {
                System.out.println("\n  [ 시스템 ] 심사를 취소하고 이전 메뉴로 돌아갑니다.");
                return;
            }

            // 대기 학생 리스트 중 입력한 enrollmentId가 있으면
            WaitingEnrollmentResponse targetStudent = null;
            for (WaitingEnrollmentResponse response : waitingList) {
                if (response.getEnrollmentId() == enrollmentId) {
                    targetStudent = response;
                    break;
                }
            }

            if (targetStudent == null) {
                System.out.println("\n  🚨 [오류] 목록에 존재하지 않는 신청 번호입니다. 다시 확인해 주세요.");
                return;
            }

            System.out.println("\n  [ 시스템 ] 👤 신청자명 : " + targetStudent.getName() + " (마을: " + targetStudent.getVillageName() + ")");
            System.out.println("  🚨 위 학생의 가입을 어떻게 처리하시겠습니까?");
            System.out.print("  ▶ [ 1 ] ✅ 승인   [ 2 ] ❌ 거절   [ 0 ] 🔙 취소 : ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                controller.approveEnrollment(villageId, enrollmentId);
                System.out.println("\n  🎉 [ 시스템 ] 승인 완료! [" + targetStudent.getName() + "] 학생이 마을 주민이 되었습니다.");
            } else if (choice.equals("2")) {
                controller.rejectEnrollment(villageId, enrollmentId);
                System.out.println("\n  [ 시스템 ] ❌ 가입이 거절 처리되었습니다.");
            } else if (choice.equals("0")) {
                System.out.println("\n  [ 시스템 ] 심사를 취소했습니다.");
            } else {
                System.out.println("\n  🚨 [오류] 잘못된 입력입니다. 1, 2, 0 중에서 선택해 주세요.");
            }


        } catch (Exception e) {
            outputView.displayFailure(e.getMessage());
        }
    }

    // 추방 기능
    private void expelEnrollment(long villageId) {
        try {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🚨 수강생 강제 추방 (Expel)                    ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 현재 마을의 승인된 전체 수강생 목록을 조회합니다...\n");
            List<ApprovedEnrollmentResponse> approvedList = controller.findApprovedEnrollmentList(villageId);

            if (approvedList == null || approvedList.isEmpty()) {
                System.out.println("  [ 시스템 ] 현재 마을에 소속된 수강생이 없습니다.");
                return;
            }

            outputView.displayApprovedEnrollments(approvedList);

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 추방할 학생의 '수강 번호(ID)'를 입력해 주세요 (취소는 '0') : ");
            long InputEnrollmentId;
            try {
                InputEnrollmentId = Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n  🚨 [오류] 번호는 숫자만 입력 가능합니다.");
                return;
            }

            if (InputEnrollmentId == 0) {
                System.out.println("\n  [ 시스템 ] 추방 처리를 취소하고 이전 메뉴로 돌아갑니다.");
                return;
            }

            ApprovedEnrollmentResponse targetStudent = null;
            for (ApprovedEnrollmentResponse student : approvedList) {
                if (student.getEnrollmentId() == InputEnrollmentId) {
                    targetStudent = student;
                    break;
                }
            }

            if (targetStudent == null) {
                System.out.println("\n  🚨 [오류] 해당 마을에 존재하지 않는 수강 번호입니다.");
                return;
            }

            System.out.println("\n  🚨 [경고] 정말로 [" + targetStudent.getName() + "] 님을 강제 추방하시겠습니까?");
            System.out.print("  ▶ 실행하시려면 'Y'를 입력해 주세요 (취소는 아무 키) : ");

            String confirm = sc.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                // 🌟 DAO는 int를 반환하지만, 우리는 예외 흐름으로 성공 여부를 판단하므로 void처럼 호출!
                controller.expelEnrollment(villageId, InputEnrollmentId);
                System.out.println("\n  🎉 [ 시스템 ] 추방 완료! 해당 학생은 더 이상 마을에 접근할 수 없습니다.");
            } else {
                System.out.println("\n  [ 시스템 ] 추방 처리가 취소되었습니다.");
            }

        } catch (Exception e) {
            System.out.println("\n  🚨 [오류] 처리 중 문제가 발생했습니다: " + e.getMessage());
        }
    }
}
