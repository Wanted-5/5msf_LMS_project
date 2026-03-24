package com.lms.domain.city.view;

import com.lms.Application;
import com.lms.domain.city.controller.CityController;
import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.domain.city.dto.request.UpdateCityRequest;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.city.dto.response.UpdateCityResponse;
<<<<<<< HEAD
import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.dto.UserRole;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.response.CreateVillageResponse;
=======
import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.domain.users.service.UserService;
>>>>>>> eb58bcbe2084f6f92bafc69820cff2a086fed614
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;

import java.util.List;
import java.util.Scanner;

//TODO : 비활성화 시키기는 추후 구현
public class CityInputView {

    private final CityController controller;
    private final CityOutputView cityOutputView;
    private final UserController userController;
    private final VillageController villageController;
    private final EnrollmentController enrollmentController;

    Scanner sc = new Scanner(System.in);

    public CityInputView(CityController controller, CityOutputView cityOutputView, UserController userController, VillageController villageController, EnrollmentController enrollmentController) {
        this.controller = controller;
        this.cityOutputView = cityOutputView;
        this.userController = userController;
        this.villageController = villageController;
        this.enrollmentController = enrollmentController;
    }
    public void displayCityAdminMenu() {

        while (true) {

            if (UserSession.getLoggedInUser().getRole() == null ||
                    UserSession.getLoggedInUser().getRole() != UserRole.ADMIN) {
                System.out.println("\n🚨 [경고] 시청 관제센터는 최고 관리자(ADMIN)만 출입할 수 있습니다.");
                return;
            }

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🏛️ 시청 관제센터 (도시 관리)                     ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("      [ 1 ] 새로운 도시 건설");
            System.out.println("      [ 2 ] 전체 도시 조감도 확인");
            System.out.println("      [ 3 ] 도시 정보 재건축");
            System.out.println("      [ 4 ] 도시 상태 변경 (활성화 ↔ 비활성화 토글)");
            System.out.println("      [ 5 ] 신규 마을 개척 (생성)");
<<<<<<< HEAD
            System.out.println("      [ 6 ] 관할 마을 입장");
            System.out.println("      [ 7 ] 권한 승격 (학생 -> 강사) ");
            System.out.println("      [ 8 ] 관리자 시스템 로그아웃");
=======
            System.out.println("      [ 6 ] 관할 마을 현장 시찰 (마을 입장)");
            System.out.println("      [ 7 ] 역할 부여 ");
            System.out.println("      [ 0 ] 관리자 시스템 로그아웃");
>>>>>>> eb58bcbe2084f6f92bafc69820cff2a086fed614
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 행정 업무의 번호를 입력해주세요 : ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 새로운 도시를 건설합니다...");
                    createCityProcess();
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] 전체 도시의 조감도를 불러옵니다...");
                    readCityProcess();
                    break;
                case "3":
                    System.out.println("\n  [ 시스템 ] 도시 재건축 프로세스를 가동합니다...");
                    updateCityProcess();
                    break;
                case "4":
                    System.out.println("\n  [ 시스템 ] 도시 상태 토글 프로세스를 준비합니다...");
                    deactivateCityProcess();
                    break;
                case "5":
                    System.out.println("\n  [ 시스템 ] 신규 마을 개척 프로세스를 가동합니다...");
                    routeToCreateVillage();
                    break;
                case "6":
                    // comment, 구현, 연동 OK
                    System.out.println("\n  [ 시스템 ] 마을에 입장합니다...");
                    routeToEnterVillage();
                    break;
                case "7":
<<<<<<< HEAD
                    // comment, 구현, 연동 OK
                    System.out.println("\n  [ 시스템 ] 권한을 수정합니다...");
                    promoteUserToInstructor();
                    break;
                case "8":
=======
                    System.out.println("\n  [ 시스템 ] 신청 대기 중인 유저 목록을 조회합니다...");
                    assignInstructorRoleFromWaitingList();
                    break;
                case "0":
>>>>>>> eb58bcbe2084f6f92bafc69820cff2a086fed614
                    System.out.println("  [ 시스템 ] 관리자 계정에서 안전하게 로그아웃 되었습니다.");
                    UserSession.setLoggedInUser(null);
                    AppContext.getAppContext().userAppContext.userInputView.displayInitialMenu();
                default:
                    System.out.println("\n  [🚨] 올바른 업무 번호(0~7)를 입력해주세요.");
            }
        }

    }

    // 도시 생성
    private void createCityProcess() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🏗️ 신규 도시 건설 프로젝트 기안                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 관리자님의 비전을 담아 새로운 도시의 기반을 다집니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.print("  ▶ 건설할 도시의 이름 (예: Wanted - MCP캠프) : ");
        String cityName = sc.nextLine();

        System.out.print("  ▶ 도시 상세 설명 (건설 목적 및 특징, 200자 이내) : ");
        String description = sc.nextLine();

        Long currentAdminId = UserSession.getLoggedInUser().getUserId();

        System.out.println("\n  [ 시스템 ] 기안서를 바탕으로 도시 건설을 시작합니다...");


        try {

            CreateCityRequest request = new CreateCityRequest(currentAdminId, cityName, description);

            CreateCityResponse response = controller.createCityProcess(request);

            cityOutputView.displayCreateSuccess(response);

        } catch (Exception e) {
            cityOutputView.displayFailure(e.getMessage());
        }


    }

    // 도시 전체 조회
    private void readCityProcess() {
        List<CityDTO> cityList = null;
        try {
            cityList = controller.readCityProcess();

            if (cityList == null || cityList.isEmpty()) {
                System.out.println("\n  [ 시스템 ] 현재 건설된 도시가 하나도 없습니다.");
                return;
            }

            cityOutputView.displaySelectSuccess(cityList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 도시 수정
    private void updateCityProcess() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🏗️ 도시 정보 재건축 프로세스                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 기존 도시의 인프라와 행정 정보를 새롭게 단장합니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        // 도시 전체 조회
        System.out.println("  [ 시스템 ] 먼저 현재 건설된 도시 목록을 불러옵니다...");
        readCityProcess(); // 앞서 만든 전체 조회 메서드 재활용!

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 재건축(수정)할 도시의 행정 코드(ID)를 입력하세요 : ");
        long cityId;
        try {
            // 문자열로 받고 -> 공백 제거 -> long타입으로 변경
            String input =  sc.nextLine().trim();
            cityId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("\n🚨 [오류] 행정 코드(ID)는 숫자만 입력 가능합니다.");
            return;
        }

        System.out.print("  ▶ 리모델링할 새로운 도시 이름 : ");
        String newCityName = sc.nextLine();

        System.out.print("  ▶ 변경할 새로운 상세 설명 : ");
        String newDescription = sc.nextLine();

        System.out.println("\n  [ 시스템 ] 안전모를 착용하고 재건축 공사를 시작합니다...");

        UpdateCityRequest request = new UpdateCityRequest(cityId, newCityName, newDescription);
        try {
            UpdateCityResponse response = controller.updateCityProcess(request);

            cityOutputView.displayUpdateSuccress(response);
        } catch (Exception e) {
            cityOutputView.displayFailure(e.getMessage());
        }

    }

    private void deactivateCityProcess() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ✅ 🔄 ❌ 도시 상태 변경 (토글) 프로세스          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 비활성화된 도시는 조감도에서 숨겨지며 학생 신규 등록이 제한됩니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.println("  [ 시스템 ] 현재 관할 중인 활성 도시 목록을 불러옵니다...");
        readCityProcess();

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 상태를 변경할 도시의 행정 코드(ID)를 입력하세요 : ");
        long cityId;

        try {
            String input = sc.nextLine().trim();
            cityId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("\n🚨 [오류] 행정 코드(ID)는 숫자만 입력 가능합니다.");
            return;
        }

        System.out.println("\n  🚨 [확인] 해당 도시의 활성화/비활성화 상태를 반전시키겠습니까?");
        System.out.print("  ▶ 동의하시면 'Y' / 'y'를 입력해 주세요 (취소는 아무 키) : ");
        String confirm = sc.nextLine();

        if (!(confirm.equals("Y") || confirm.equals("y"))) {
            System.out.println("\n  [ 시스템 ] 철거 명령이 취소되었습니다. 관제센터로 돌아갑니다.");
            return;
        }

        System.out.println("\n  [ 시스템 ] 도시 출입 통제망을 가동합니다. 도시를 비활성화합니다...");

        try {
            Boolean finalStatus = controller.deactivateCityProcess(cityId);

            cityOutputView.displayUpdateStatusSuccess(finalStatus, cityId);
        } catch (Exception e) {
            cityOutputView.displayFailure(e.getMessage());
        }
    }

    // 마을 생성 전 도시번호 선택하는 메서드
    private void routeToCreateVillage() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🔀 신규 마을 개척 대상 도시 선택             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 마을을 개척할 상위 행정 구역(도시)을 선택해야 합니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.println("  [ 시스템 ] 현재 관할 중인 도시 조감도를 불러옵니다...\n");
        readCityProcess();

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 마을을 개척할 도시의 행정 코드(ID)를 입력하세요 : ");
        long cityId;
        try {
            String input = sc.nextLine().trim();
            cityId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("\n🚨 [오류] 행정 코드(ID)는 숫자만 입력해야 합니다. 메인 메뉴로 돌아갑니다.");
            return;
        }

        System.out.println("\n  [ 시스템 ] 행정 코드 [ " + cityId + " ] 도시로 마을 개척 업무를 이관합니다...");
        AppContext.getAppContext().villageAppContext.adminVillageInputView.createVillageProcess(cityId);
    }

    // 권한 승격
    private void promoteUserToInstructor() {
        if (UserSession.getLoggedInUser().getRole() != UserRole.ADMIN) {
            System.out.println("\n  [ 시스템 ] 관리자 전용 메뉴입니다.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 👑 운영진 권한 승격 (Promote)                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.print("  ▶ 강사로 승격시킬 유저의 아이디(Username) 입력 : ");
        String username = sc.nextLine().trim();

        try {
            userController.updateRoleToInstructor(username);
            System.out.println("\n  🎉 [ 성공 ] " + username + " 님이 강사(INSTRUCTOR)로 승격되었습니다.");
        } catch (Exception e) {
            System.out.println("\n  🚨 [ 실패 ] " + e.getMessage());
        }
    }


    private void routeToEnterVillage() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🌐 전 지역 마을 통합 관제 시스템                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 관리자 권한으로 전체 마을 리스트를 동기화합니다...\n");

        List<CreateVillageResponse> villageList = villageController.findAllVillages();

        if (villageList == null || villageList.isEmpty()) {
            System.out.println("  [ 시스템 ] 현재 개척된 마을이 하나도 없습니다.");
            return;
        }

        cityOutputView.displayVillageList(villageList);

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 직권 입장할 마을의 고유 번호(ID)를 입력하세요 (취소: 0) : ");

        long villageId;
        try {
            villageId = Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\n [🚨] 마을 번호는 숫자만 입력 가능합니다.");
            return;
        }

        if (villageId == 0) return;

        System.out.println("\n  [ 시스템 ] 관리자 직권으로 마을 출입 보안망을 해제합니다...");
        enrollmentController.enterVillageByAdmin(villageId);

        AppContext.getAppContext().villageAppContext.instructorVillageInputView.displayInstructorMainMenu(villageId);

    }

    private void assignInstructorRoleFromWaitingList() {
        try {
            java.util.List<java.util.Map<String, Object>> waitingList =
                    AppContext.getAppContext()
                            .enrollmentAppContext
                            .enrollmentController
                            .findAllWaitingEnrollmentList();

            if (waitingList == null || waitingList.isEmpty()) {
                System.out.println("  [ 시스템 ] 현재 신청 대기 중인 유저가 없습니다.");
                return;
            }

            System.out.println("\n=== 신청 대기 중인 유저 목록 ===");

            for (java.util.Map<String, Object> row : waitingList) {
                System.out.println("신청번호: " + row.get("enrollmentId")
                        + " | 유저명: " + row.get("userName")
                        + " | 마을명: " + row.get("villageName")
                        + " | 상태: " + row.get("status"));
            }

            System.out.print("\n강사로 승격할 신청 번호(enrollment_id)를 입력하세요 : ");
            long enrollmentId = Long.parseLong(sc.nextLine());

            java.util.Map<String, Object> target = null;
            for (java.util.Map<String, Object> row : waitingList) {
                long rowId = ((Number) row.get("enrollmentId")).longValue();
                if (rowId == enrollmentId) {
                    target = row;
                    break;
                }
            }

            if (target == null) {
                System.out.println("  [ 시스템 ] 존재하지 않는 신청 번호입니다.");
                return;
            }

            System.out.print("정말로 [" + target.get("userName") + "] 님을 강사로 승격하시겠습니까? (Y/N) : ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                AppContext.getAppContext()
                        .enrollmentAppContext
                        .enrollmentController
                        .promoteWaitingStudentToInstructor(enrollmentId);

                System.out.println("  [ 시스템 ] 강사 승격이 완료되었습니다.");
            } else {
                System.out.println("  [ 시스템 ] 취소되었습니다.");
            }

        } catch (NumberFormatException e) {
            System.out.println("  [ 시스템 ] 신청 번호는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println("  [ 시스템 ] 오류: " + e.getMessage());
        }
    }
}
