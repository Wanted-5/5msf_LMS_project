package com.lms.domain.section.view;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.DTO.SectionDTO;
import com.lms.domain.village.dto.VillageDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SectionInputView {

    private final SectionController sectionController;
    private final Scanner sc = new Scanner(System.in);

    // 현재 실행 중인 동안만 완료 강의 저장
    private final Set<Long> completedSectionIds = new HashSet<>();

    public SectionInputView(SectionController sectionController) {
        this.sectionController = sectionController;
    }

    public void displaySectionMenu(VillageDTO village) {
        while (true) {
            System.out.println("\n=== 교육센터 ===");
            System.out.println("마을: " + village.getVillageName());
            System.out.println("1. 수강 강의 목록 조회");
            System.out.println("2. 학습 시작");
            System.out.println("3. 완료한 학습 목록 보기");
            System.out.println("0. 뒤로가기");
            System.out.print("번호 입력 : ");

            int menu;
            try {
                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            switch (menu) {
                case 1:
                    showUncompletedSections(village.getVillageId());
                    break;
                case 2:
                    startLearning(village.getVillageId());
                    break;
                case 3:
                    showCompletedSections(village.getVillageId());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 1. 미수강 강의 목록 조회
    private void showUncompletedSections(long villageId) {
        List<SectionDTO> sections = sectionController.getSectionsByVillageId(villageId);

        boolean found = false;

        System.out.println("\n=== 수강 강의 목록 ===");
        for (SectionDTO section : sections) {
            if (!completedSectionIds.contains(section.getSectionId())) {
                System.out.println(section.getSectionId() + ". ["
                        + section.getChapNo() + "주차] "
                        + section.getSectionName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("강의가 없습니다.");
        }
    }

    // 2. 학습 시작
    private void startLearning(long villageId) {
        List<SectionDTO> sections = sectionController.getSectionsByVillageId(villageId);

        boolean found = false;

        System.out.println("\n=== 미수강 강의 목록 ===");
        for (SectionDTO section : sections) {
            if (!completedSectionIds.contains(section.getSectionId())) {
                System.out.println(section.getSectionId() + ". ["
                        + section.getChapNo() + "주차] "
                        + section.getSectionName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("학습할 미수강 강의가 없습니다.");
            return;
        }

        System.out.print("학습할 강의 번호 입력: ");

        long sectionId;
        try {
            sectionId = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력하세요.");
            return;
        }

        SectionDTO section = sectionController.getSectionByVillageIdAndSectionId(villageId, sectionId);

        if (section == null) {
            System.out.println("해당 마을의 강의가 아닙니다.");
            return;
        }

        if (completedSectionIds.contains(sectionId)) {
            System.out.println("이미 완료한 강의입니다.");
            return;
        }

        System.out.println("\n=== 학습 시작 ===");
        System.out.println("강의명: " + section.getSectionName());
        System.out.println("내용: " + section.getContent());

        showLoadingBar();

        completedSectionIds.add(sectionId);

        System.out.println("\n수강 완료!");
        System.out.println("완료 강의: " + section.getSectionName());
    }

    // 3. 완료한 학습 목록 보기
    private void showCompletedSections(long villageId) {
        List<SectionDTO> sections = sectionController.getSectionsByVillageId(villageId);

        boolean found = false;

        System.out.println("\n=== 완료한 학습 목록 ===");
        for (SectionDTO section : sections) {
            if (completedSectionIds.contains(section.getSectionId())) {
                System.out.println(section.getSectionId() + ". ["
                        + section.getChapNo() + "주차] "
                        + section.getSectionName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("완료한 학습이 없습니다.");
        }
    }

    private void showLoadingBar() {
        System.out.print("학습 진행 중 [");

        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(100); // 약 3초
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\n학습이 중단되었습니다.");
                return;
            }
            System.out.print("=");
        }

        System.out.println("]");
    }
}

