package com.lms.domain.section.view;

import com.lms.domain.section.dto.response.SectionListResponse;

import java.util.List;

public class SectionOutputView {

    public void displaySectionList(List<SectionListResponse> sectionList) {
        for (SectionListResponse section : sectionList) {
            // 향후 ✅/❌ 기능이 들어갈 자리를 비워두고, 현재 상태(ACTIVE 등)를 깔끔하게 매핑합니다.
            System.out.printf("  ▶ [ %2d주차 ] %s (강의 번호: %d) - 상태: %s\n",
                    section.getChapNo(),
                    section.getSectionName(),
                    section.getSectionId(),
                    section.getStatus());
        }

        System.out.println("────────────────────────────────────────────────────────────────");
    }


}
