package com.lms.domain.section.view;

import com.lms.domain.learning.dto.LearningDTO;
import com.lms.domain.learning.dto.reseponse.LearningSectionResponse;
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

    public void displaySectionsByStatus(List<LearningSectionResponse> list, String statusDisplayName) {

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.println("  📌 현재 조회 상태 : " + statusDisplayName);
        System.out.println("────────────────────────────────────────────────────────────────");

        // 🌟 복잡한 로직(if, match 등)은 1도 없습니다. 그저 리스트를 순회하며 출력할 뿐!
        for (LearningSectionResponse response : list) {
            System.out.printf("  ▶ %2d번. [ %2d주차 ] %s\n",
                    response.getSectionId(),
                    response.getChapNo(),
                    response.getSectionName()
            );
        }

        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    public void displayCreateSectionSuccess(int studentCount) {
        System.out.println("\n  🎉 [ 성공 ] 새로운 강의가 성공적으로 배포되었습니다!");

        if (studentCount > 0) {
            System.out.println("  [ 알림 ] " + studentCount + "명의 마을 주민에게 '수강 전' 이력이 생성되었습니다.");
        } else {
            System.out.println("  [ 알림 ] 현재 마을에 수강생이 없어 강의만 등록되었습니다.");
        }
    }

    // 🌟 섹션 관련 에러 메시지 출력
    public void displaySectionError(String message) {
        System.out.println("\n  🚨 [ 섹션 오류 ] 작업을 완료하지 못했습니다.");
        System.out.println("  ▶ 사유: " + message);
    }



}
