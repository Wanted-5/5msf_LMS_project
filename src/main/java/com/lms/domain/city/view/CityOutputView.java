package com.lms.domain.city.view;

import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.response.CreateCityResponse;

import java.util.List;

public class CityOutputView {
    public void displayCreateSuccess(CreateCityResponse response) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎉 도시 건설 완료 🎉                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  ▶ 부여된 행정 코드(ID) : " + response.getCityId());
        System.out.println("  ▶ 완공된 도시 이름     : " + response.getCityName());
        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    public void displayFailure(String errorMessage) {
        System.out.println("\n🚨 건설 실패: " + errorMessage + "\n");
    }

    public void displaySelectSuccess(List<CityDTO> cityList) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🗺️ 전체 도시 조감도 현황                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        for (CityDTO city : cityList) {
            // 상태(Boolean) 값을 예쁜 문자열로 변환
            String statusString = city.isActive() ? "🟢 활성화" : "🔴 철거 대기 (비활성화)";

            System.out.printf("  [ 행정 코드 ID : %d ] %s\n", city.getCityId(), city.getCityName());
            System.out.println("   ▶ 도 시 상 태  : " + statusString);
            System.out.println("   ▶ 상 세 설 명  : " + city.getDescription());
            // 만약 CityDTO에 생성자ID(creatorId)나 생성일(createdAt)이 있다면 아래처럼 추가하셔도 좋습니다!
             System.out.println("   ▶ 건 설 자 ID  : " + city.getCreatorId());
             System.out.println("   ▶ 최 초 건 설 시 간  : " + city.getCreated_at());
            System.out.println("────────────────────────────────────────────────────────────────");
        }

        System.out.println("  [ 시스템 ] 총 " + cityList.size() + "개의 도시 조감도 출력이 완료되었습니다.\n");
    }
}
