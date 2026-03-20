package com.lms.domain.city.view;

import com.lms.domain.city.controller.CityController;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.users.constant.UserRole;
import com.lms.global.common.UserSession;

import java.util.Scanner;

public class CityInputView {

    private final CityController controller;
    private final CityOutputView outputView;

    Scanner sc = new Scanner(System.in);

    public CityInputView(CityController controller, CityOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
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
            System.out.println("      [ 1 ] 새로운 도시 건설 (Create)");
            System.out.println("      [ 2 ] 전체 도시 조감도 확인 (Read)");
            System.out.println("      [ 3 ] 도시 정보 재건축 (Update)");
            System.out.println("      [ 4 ] 도시 철거 (Delete)");
            System.out.println("      [ 0 ] 이전 관리자 메인으로 돌아가기 (관제센터 퇴장)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 행정 업무의 번호를 입력해주세요 : ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 새로운 도시를 건설합니다...");
                    // TODO: 도시 건설 로직 (readCityProcess)
                    createCityProcess(); // 도시 건설 로직 호출
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] 전체 도시의 조감도를 불러옵니다...");
                    // TODO: 조감도 확인 로직 (readCityProcess)
                    break;
                case "3":
                    System.out.println("\n  [ 시스템 ] 도시 재건축 프로세스를 가동합니다...");
                    // TODO: 정보 수정 로직 (updateCityProcess)
                    break;
                case "4":
                    System.out.println("\n  [ 시스템 ] 철거 장비를 준비합니다...");
                    // TODO: 삭제 로직 (deleteCityProcess)
                    break;
                case "0":
                    System.out.println("\n  🚪 관제센터의 문을 닫고 나갑니다.");
                    return;
                default:
                    System.out.println("\n  [🚨] 올바른 업무 번호(0~4)를 입력해주세요.");
            }
        }
    }

    private void createCityProcess() {
        System.out.println("새로운 도시 이름 입력 : ");
        String cityName = sc.nextLine();
        System.out.println("새로운 도시 설명 입력 : ");
        String description = sc.nextLine();

        CreateCityResponse response = controller.createCityProcess(cityName, description);

    }
}
