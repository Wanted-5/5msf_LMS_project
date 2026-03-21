package com.lms.domain.attendance.view;

import com.lms.domain.attendance.controller.AttendController;
import com.lms.domain.attendance.dto.AttendDTO;
import com.lms.domain.users.constant.UserRole;
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
    LoginResponse loginUser = UserSession.getLoggedInUser();

    public AttendInputView(AttendController attendController, AttendOutputView attendOutputView) {
        this.attendController = attendController;
        this.attendOutputView = attendOutputView;
    }

    public void AttendMenu() {
        if (!UserSession.isLoggedIn()) {
            attendOutputView.printError("출결 메뉴는 로그인 후 이용 가능합니다.");
            return;
        }

        if (loginUser.getRole() == UserRole.ADMIN || loginUser.getRole() == UserRole.INSTRUCTOR) {
            AdminAttendMenu();
        } else {
            StudentAttendMenu();
        }
    }

    public void StudentAttendMenu() {

        while (true) {
            System.out.println("\n==== 🧑‍🎓 [학생] 출결 메뉴 ====");
            System.out.println("1. 출석체크 하기");
            System.out.println("2. 출결 확인");
            System.out.println("3. 메인 메뉴로 돌아가기");
            System.out.print("번호를 입력해 주세요 : ");

            try {
                int menu1 = Integer.parseInt(sc.nextLine());

                switch (menu1) {
                    case 1:
                        AttendDTO newAttend = new AttendDTO();
                        newAttend.setAttendanceId(System.currentTimeMillis());
                        newAttend.setVillageId(1L);
                        newAttend.setUserId(loginUser.getUserId());
                        attendController.processAttendanceCheck(newAttend);
                        break;
                    case 2:
                        List<AttendDTO> myList = attendController.viewMyAttendance(loginUser.getUserId());
                        attendOutputView.printAttendanceList(myList);
                        break;
                    case 3:
                        System.out.println("메인 메뉴로 돌아갑니다.");
                        return;
                    default:
                        attendOutputView.printError("1~3 사이의 숫자를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                attendOutputView.printError("입력 오류: 숫자만 입력해주세요!");
            }
        }
    }

    public void AdminAttendMenu() {
        while (true) {
            System.out.println("\n==== 👨‍🏫 [관리자] 출결 관리 메뉴 ====");
            System.out.println("1. 수강생 전체 출결 조회(최신순)");
            System.out.println("2. 수강생 출결 관리 (수정/삭제)");
            System.out.println("3. 이전 메뉴로 돌아가기");
            System.out.print("번호를 입력해 주세요 : ");

            try {
                int menu2 = Integer.parseInt(sc.nextLine());

                switch (menu2) {
                    case 1:
                        List<AttendDTO> allList = attendController.viewAllAttendance();
                        attendOutputView.printAttendanceList(allList);
                        break;
                    case 2:
                        AttendUpdate();
                        break;
                    case 3:
                        System.out.println("메인 메뉴로 돌아갑니다.");
                        return;
                    default:
                        attendOutputView.printError("올바른 번호(1~3)를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                attendOutputView.printError("입력 오류: 숫자만 입력해주세요!");
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
                System.out.print("관리할 출결 번호(attendance_id)를 입력하세요 : ");
                long targetId = Long.parseLong(sc.nextLine());

                System.out.println("\n무엇을 하시겠습니까?");
                System.out.println("1. 상태 수정하기  |  2. 기록 삭제하기  |  3. 취소");
                System.out.print("선택 : ");
                int action = Integer.parseInt(sc.nextLine());

                if (action == 1) {
                    System.out.print("변경할 상태를 입력하세요 (출석, 결석, 지각, 조퇴, 공결) : ");
                    String statusKor = sc.nextLine().trim();

                    boolean isSuccess = attendController.updateAttendStatus(targetId, statusKor);

                    if (isSuccess) {
                        attendOutputView.printSuccess("출결 상태가 성공적으로 수정되었습니다.");
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
