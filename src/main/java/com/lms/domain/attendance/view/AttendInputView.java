package com.lms.domain.attendance.view;

import com.lms.domain.attendance.controller.AttendController;
import com.lms.domain.attendance.dto.AttendDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.global.common.UserSession;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AttendInputView {
    private final AttendController attendController;
    private final AttendOutputView attendOutputView;
    Scanner sc = new Scanner(System.in);

    public AttendInputView(AttendController attendController, AttendOutputView attendOutputView) {
        this.attendController = attendController;
        this.attendOutputView = attendOutputView;
    }

    public void AttendMenu(Long villageId) {
        LoginResponse loginUser = UserSession.getLoggedInUser();

        if (loginUser.getRole() == UserRole.ADMIN || loginUser.getRole() == UserRole.INSTRUCTOR) {
            AdminAttendMenu(villageId);
        } else {
            StudentAttendMenu(villageId);
        }
    }

    public void StudentAttendMenu(Long villageId) {
        LoginResponse loginUser = UserSession.getLoggedInUser();

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 📅 마을 교육센터 - 출입/출결 시스템 (학생용)       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 교육센터의 일일 출석을 인증하고 누적 기록을 열람합니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] ✅ 금일 출석 인증 (Check-in)");
            System.out.println("      [ 2 ] 📊 나의 누적 출결 기록 열람");
            System.out.println("      [ 3 ] ↩️ 출결 시스템 종료 (마을 광장 복귀)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 업무 번호를 입력해주세요 : ");

            try {
                int menu1 = Integer.parseInt(sc.nextLine());

                switch (menu1) {
                    case 1:
                        System.out.println("\n  [ 시스템 ] 현재 시간부로 교육센터 출입(출석) 인증 프로세스를 가동합니다...");
                        AttendDTO newAttend = new AttendDTO();
                        newAttend.setVillageId(villageId);
                        newAttend.setUserId(loginUser.getUserId());
                        newAttend.setAttendanceDate(java.time.LocalDateTime.now());
                        attendController.processAttendanceCheck(newAttend);
                        break;
                    case 2:
                        System.out.println("\n  [ 시스템 ] 데이터베이스에서 본인의 누적 출결 기록을 동기화합니다...");
                        List<AttendDTO> myList = attendController.viewMyAttendance(loginUser.getUserId());
                        attendOutputView.printAttendanceList(myList);
                        break;
                    case 3:
                        System.out.println("\n  [ 시스템 ] 출결 관리 시스템을 안전하게 종료하고 이전 화면으로 복귀합니다.");
                        return;
                    default:
                        attendOutputView.printError("존재하지 않는 업무 번호입니다. 1~3 사이의 숫자를 입력해 주세요.");
                }
            } catch (NumberFormatException e) {
                attendOutputView.printError("입력 오류: 업무 번호는 숫자만 입력 가능합니다.");
            }
        }
    }

    public void AdminAttendMenu(Long villageId) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 👨‍🏫 마을 교육센터 - 출결 통합 관제실 (관리자용)     ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 마을 전체 수강생의 출결 현황을 감독하고 기록을 관리합니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📊 수강생 전체 출결 조감도 열람 (최신순)");
            System.out.println("      [ 2 ] ⚙️ 특정 수강생 출결 기록 재정비 (수정/철거)");
            System.out.println("      [ 3 ] ↩️ 관제실 퇴장 및 이전 메뉴 복귀");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 행정 업무 번호를 입력해주세요 : ");

            try {
                int menu2 = Integer.parseInt(sc.nextLine());

                switch (menu2) {
                    case 1:
                        System.out.println("\n  [ 시스템 ] 전체 수강생의 최신 출결 데이터를 서버에서 동기화합니다...");
                        List<AttendDTO> allList = attendController.viewAllAttendance();
                        attendOutputView.printAttendanceList(allList);
                        break;
                    case 2:
                        System.out.println("\n  [ 시스템 ] 수강생 출결 기록 재정비 프로세스를 가동합니다...");
                        AttendUpdate();
                        break;
                    case 3:
                        System.out.println("\n  [ 시스템 ] 출결 관제실을 안전하게 종료하고 이전 화면으로 복귀합니다.");
                        return;
                    default:
                        attendOutputView.printError("존재하지 않는 업무 번호입니다. 올바른 번호(1~3)를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                attendOutputView.printError("입력 오류: 업무 번호는 숫자만 입력 가능합니다.");
            }
        }
    }

    public void AttendUpdate() {
        while (true) {
            System.out.println("\n==== 🛠️ [관리자] 수강생 출결 관리 ====");
            System.out.println("(예시 : 2026-03-21, 취소하려면 'q' 입력)");
            System.out.print("조회할 날짜를 입력해 주세요 : ");
            String dateInput = sc.nextLine().trim();

            if (dateInput.equalsIgnoreCase("q")) {
                return;
            }
            try {
                LocalDate targetDate = LocalDate.parse(dateInput);
                List<AttendDTO> list = attendController.viewAttendanceByDate(targetDate);

                if (list.isEmpty()) {
                    attendOutputView.printError("해당 날짜에는 출결 기록이 없습니다.");
                    continue;
                }

                attendOutputView.printAttendanceList(list);
                System.out.print("관리할 순번( [ ] 안의 번호)을 입력하세요 : ");
                int selectNum = Integer.parseInt(sc.nextLine());

                AttendDTO selectedAttend = list.get(selectNum - 1);
                long targetId = selectedAttend.getAttendanceId();

                System.out.println("\n선택된 기록: [" + selectedAttend.getUserName() + "] " + selectedAttend.getStatus().getDescription());
                System.out.println("무엇을 하시겠습니까?");
                System.out.println("1. 상태 수정하기  |  2. 기록 삭제하기  |  3. 취소");
                System.out.print("선택 : ");
                int action = Integer.parseInt(sc.nextLine());

                if (action == 1) {
                    System.out.print("변경할 상태를 입력하세요 (출석, 결석, 지각, 조퇴, 공결) : ");
                    String statusKor = sc.nextLine().trim();

                    boolean isSuccess = attendController.updateAttendStatus(targetId, statusKor);

                    if (isSuccess) {
                        attendOutputView.printSuccess("출결 상태가 성공적으로 수정되었습니다.");
                        List<AttendDTO> updatedList = attendController.viewAttendanceByDate(targetDate);
                        attendOutputView.printAttendanceList(updatedList);
                    } else {
                        attendOutputView.printError("출결 상태 수정에 실패했습니다. 상태명을 정확히 입력해주세요.");
                    }

                } else if (action == 2) {
                    System.out.print("정말 삭제하시겠습니까? (y/n) : ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        boolean isSuccess = attendController.deleteAttendance(targetId);
                        if (isSuccess) {
                            attendOutputView.printSuccess("출결 기록이 완전히 삭제되었습니다.");
                        } else {
                            attendOutputView.printError("출결 기록 삭제에 실패했습니다.");
                        }
                    } else {
                        System.out.println("삭제 작업이 취소되었습니다.");
                    }
                } else {
                    System.out.println("작업이 취소되었습니다.");
                }
            } catch (DateTimeParseException e) {
                attendOutputView.printError("날짜 형식이 올바르지 않습니다. YYYY-MM-DD 형식으로 입력해주세요.");
            } catch (NumberFormatException e) {
                attendOutputView.printError("번호 입력이 잘못되었습니다. 숫자로 입력해주세요.");
            }
        }
    }

}
