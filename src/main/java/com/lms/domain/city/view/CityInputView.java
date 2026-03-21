package com.lms.domain.city.view;

import com.lms.domain.city.controller.CityController;
import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.domain.city.dto.request.UpdateCityRequest;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.city.dto.response.UpdateCityResponse;
import com.lms.domain.users.constant.UserRole;
import com.lms.global.common.UserSession;

import java.util.List;
import java.util.Scanner;

//TODO : 비활성화 시키기는 추후 구현
public class CityInputView {

    private final CityController controller;
    private final CityOutputView cityOutputView;

    Scanner sc = new Scanner(System.in);

    public CityInputView(CityController controller, CityOutputView cityOutputView) {
        this.controller = controller;
        this.cityOutputView = cityOutputView;
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
            System.out.println("      [ 4 ] 도시 철거");
            System.out.println("      [ 5 ] 관할 마을 현장 시찰 (마을 입장)"); // 🌟 새로 추가된 메뉴!
            System.out.println("      [ 0 ] 관리자 시스템 로그아웃");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 행정 업무의 번호를 입력해주세요 : ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 새로운 도시를 건설합니다...");
                    createCityProcess(); // 도시 건설 로직 호출
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
                    System.out.println("\n  [ 시스템 ] 철거 장비를 준비합니다...");
                    // TODO: 삭제 로직 (deleteCityProcess)
                    deleteCityProcess();
                    break;
                case "5":
                    System.out.println("\n  [ 시스템 ] 마을에 입장합니다...");
                    // TODO: 마을 입장 전 전체 리스트 조회하고 마을 번호 받아서 입장하기 (deleteCityProcess)
                    break;
                case "0":
                    System.out.println("  [ 시스템 ] 관리자 계정에서 안전하게 로그아웃 되었습니다.");
                    UserSession.setLoggedInUser(null);
                    return;
                default:
                    System.out.println("\n  [🚨] 올바른 업무 번호(0~5)를 입력해주세요.");
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

    private void deleteCityProcess() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🧨 도시 철거 및 폐쇄 프로세스                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 철거된 도시는 복구하기 어려울 수 있습니다. 주의 바랍니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.println("  [ 시스템 ] 현재 관할 중인 도시 목록을 불러옵니다...");
        readCityProcess();

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 철거를 집행할 도시의 행정 코드(ID)를 입력하세요 : ");
        long cityId;

        try {
            String input = sc.nextLine().trim();
            cityId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("\n🚨 [오류] 행정 코드(ID)는 숫자만 입력 가능합니다.");
            return;
        }
    }
}
