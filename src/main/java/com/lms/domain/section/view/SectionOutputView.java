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
    public void printSectionList(List<SectionListResponse> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("조회된 강의가 없습니다.");
            return;
        }

        for (SectionListResponse section : list) {
            System.out.println("--------------------------------");
            System.out.println("강의번호(section_id): " + section.getSectionId());
            System.out.println("주차(chap_no): " + section.getChapNo());
            System.out.println("강의명: " + section.getSectionName());
            System.out.println("상태: " + section.getStatus());
        }
        System.out.println("--------------------------------");
    }

}
