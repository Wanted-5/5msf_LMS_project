package com.lms.domain.section.view;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.DTO.SectionDTO;
import com.lms.domain.village.model.DTO.VillageDTO;

import java.util.List;
import java.util.Scanner;

public class SectionInputView {

    private final SectionController sectionController;
    private final Scanner sc = new Scanner(System.in);

    public SectionInputView(SectionController sectionController) {
        this.sectionController = sectionController;
    }

    public void displaySectionMenu(VillageDTO village) {
        while (true) {
            System.out.println("\n=== 교육센터 ===");
            System.out.println("마을: " + village.getVillageName());
            System.out.println("1. 전체 강의 목록 조회");
            System.out.println("2. 학습 시작");
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
                    showSectionsByVillage(village.getVillageId());
                    break;
                case 2:
                    startLearning();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showSectionsByVillage(long villageId) {
        List<SectionDTO> sections = sectionController.getSectionsByVillageId(villageId);

        if (sections.isEmpty()) {
            System.out.println("등록된 강의가 없습니다.");
            return;
        }

        System.out.println("\n=== 강의 목록 ===");
        for (SectionDTO section : sections) {
            System.out.println(section.getSectionId() + ". "
                    + section.getSectionName() + " - " + section.getContent());
        }
    }

    private void startLearning() {
        System.out.print("학습할 강의 번호 입력: ");

        long sectionId;
        try {
            sectionId = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력하세요.");
            return;
        }

        SectionDTO section = sectionController.getSectionById(sectionId);

        if (section == null) {
            System.out.println("해당 강의가 없습니다.");
            return;
        }

        System.out.println("\n=== 학습 시작 ===");
        System.out.println("강의명: " + section.getSectionName());
        System.out.println("설명: " + section.getContent());
    }
}