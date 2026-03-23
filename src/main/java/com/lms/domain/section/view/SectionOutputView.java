package com.lms.domain.section.view;

import com.lms.domain.section.dto.response.SectionDetailResponse;
import com.lms.domain.section.dto.response.SectionListResponse;

import java.util.List;

public class SectionOutputView {

    public void displayFailure(String errorMessage) {
        System.out.println("\n🚨 섹션 로딩 실패: " + errorMessage + "\n");
    }

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

    public void displaySectionDetail(SectionDetailResponse section) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 📖 강의 상세 정보 (Section Detail)           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // 주차, 제목, 그리고 우리가 방금 DB에 추가한 강사 번호(userId) 출력!
        System.out.printf("  ▶ [ %2d주차 ] %s\n", section.getChapNo(), section.getSectionName());
        System.out.printf("  ▶ 담당 강사 번호 : %d번\n", section.getUserId());
        System.out.println("────────────────────────────────────────────────────────────────");

        // 강의 내용 (본문)
        System.out.println("  [ 강의 소개 ]");
        System.out.println("  " + section.getContent());

        // 영상 링크 (null이거나 비어있지 않을 때만 출력하는 디테일)
        if (section.getVideoUrl() != null && !section.getVideoUrl().trim().isEmpty()) {
            System.out.println("\n  📺 영상 링크 : " + section.getVideoUrl());
        } else {
            System.out.println("\n  📺 영상 링크 : (아직 등록된 영상이 없습니다)");
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
