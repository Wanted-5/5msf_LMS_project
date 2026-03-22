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

    public void printSectionListHeader() {
        System.out.println("\n=== 전체 강의 목록 조회 ===");
    }

    public void printIncompleteSectionListHeader() {
        System.out.println("\n=== 미수강 학습 목록 조회 ===");
    }

    public void printCompletedSectionListHeader() {
        System.out.println("\n=== 완료한 학습 목록 조회 ===");
    }

    public void printNoSections() {
        System.out.println("조회된 강의가 없습니다.");
    }

    public void printNoCompletedSections() {
        System.out.println("완료한 학습이 없습니다.");
    }

    public void printSectionList(List<SectionDTO> sectionList) {
        if (sectionList == null || sectionList.isEmpty()) {
            printNoSections();
            return;
        }

        for (SectionDTO section : sectionList) {
            System.out.println("----------------------------------");
            System.out.println("섹션 ID : " + section.getSectionId());
            System.out.println("주차(chap_no) : " + section.getChapNo());
            System.out.println("강의 제목 : " + section.getSectionName());
            System.out.println("강의 내용 : " + section.getContent());

            if (section.getVideoUrl() != null && !section.getVideoUrl().isBlank()) {
                System.out.println("영상 링크 : " + section.getVideoUrl());
            } else {
                System.out.println("영상 링크 : 없음");
            }
        }
        System.out.println("----------------------------------");
    }

    public void printCompletedMessage(String sectionName) {
        System.out.println("수강 완료: " + sectionName);
    }

    public void printUploadSuccess() {
        System.out.println("=== 새로운 강의가 성공적으로 업로드되었습니다. ===");
    }

    public void printErrorMessage(String message) {
        System.out.println("오류: " + message);
    }

    public void printLoadingBar() {
        System.out.print("학습 중");
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\n로딩 중 인터럽트가 발생했습니다.");
        }
    }
}
