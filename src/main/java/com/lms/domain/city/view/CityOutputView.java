package com.lms.domain.city.view;

import com.lms.domain.city.dto.response.CreateCityResponse;

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
}
