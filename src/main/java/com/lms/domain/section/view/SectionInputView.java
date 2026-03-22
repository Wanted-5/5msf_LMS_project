package com.lms.domain.section.view;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.dto.response.SectionListResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SectionInputView {

    private final SectionController sectionController;
    private final SectionOutputView sectionOutputView;
    private final Scanner sc = new Scanner(System.in);

    // 현재 실행 중인 동안만 완료 강의 저장
    private final Set<Long> completedSectionIds = new HashSet<>();

    public SectionInputView(SectionController sectionController, SectionOutputView sectionOutputView) {
        this.sectionController = sectionController;
        this.sectionOutputView = sectionOutputView;
    }

    // 학생용 메뉴
    public void displayStudentSectionMenu(long villageId) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🎓 교육센터 (Section 학습 공간)                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 교육센터에 입장하셨습니다. 학습 진도를 관리해 보세요.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📚 수강 가능한 강의(Section) 목록 조회");
            System.out.println("      [ 2 ] ▶️ 학습 시작 (섹션 수강하기)");
            System.out.println("      [ 3 ] ✅ 수강 완료한 강의 목록 보기");
            System.out.println("      [ 0 ] 🚪 교육센터 퇴장 (마을 광장으로 돌아가기)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 메뉴의 번호를 입력해주세요 : ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 📚 수강 가능한 강의 목록을 탐색합니다...");
                    showAllSectionsProcess(villageId);
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] ▶️ 학습 시스템을 가동합니다...");
                    startLearning(villageId);
                    break;
                case "3":
                    System.out.println("\n  [ 시스템 ] ✅ 완료된 학습 기록을 조회합니다...");
                    showCompletedSections(villageId);
                    break;
                case "0":
                    System.out.println("\n  [ 시스템 ] 교육센터에서 퇴장하여 마을 광장으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("\n  🚨 [오류] 올바른 메뉴 번호(0~3)를 입력해주세요.");
            }
        }
    }

    // 강사용 메뉴
    public void displayInstructorSectionMenu(long villageId, Long userId) {
        while (true) {
            System.out.println("\n=== 교육센터 관리 ===");
            System.out.println("1. 전체 강의 보기");
            System.out.println("2. 학습 시작하기");
            System.out.println("3. 완료한 학습 목록 보기");
            System.out.println("4. 신규 섹션 업로드");
            System.out.println("5. 메인페이지로 돌아가기");
            System.out.print("번호 입력 : ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    showAllSectionsProcess(villageId);
                    break;
                case "2":
                    startLearning(villageId);
                    break;
                case "3":
                    showCompletedSections(villageId);
                    break;
                case "4":
                    createSectionProcess(villageId, userId);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("[시스템] 올바른 메뉴 번호를 입력해주세요.");
            }
        }
    }

    // 전체 강의 조회
    private void showAllSectionsProcess(long villageId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 📚 전체 강의(Section) 목록 및 현황              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 현재 마을에 개설된 전체 강의와 수강 현황을 불러옵니다...\n");

        try {
            List<SectionListResponse> sectionList = sectionController.displayAllSections(villageId);

            if (sectionList == null || sectionList.isEmpty()) {
                System.out.println("\n  [ 시스템 ] 현재 등록된 강의가 없습니다.");
                return;
            }

            sectionOutputView.displaySectionList(sectionList);
            System.out.println("  [ 시스템 ] 조회가 완료되었습니다. 상위 메뉴로 돌아갑니다.");

        } catch (Exception e) {
            System.out.println("\n  🚨 [오류] 강의 목록 조회 중 문제가 발생했습니다: " + e.getMessage());
        }
    }

    // 완료한 학습 목록 보기
    private void showCompletedSections(long villageId) {
        System.out.println("\n=== 완료한 학습 목록 보기 ===");

        try {
            List<SectionListResponse> sectionList = sectionController.displayAllSections(villageId);

            if (sectionList == null || sectionList.isEmpty()) {
                System.out.println("[시스템] 현재 등록된 강의가 없습니다.");
                return;
            }

            boolean found = false;

            for (SectionListResponse section : sectionList) {
                if (completedSectionIds.contains(section.getSectionId())) {
                    System.out.println(section.getSectionId() + ". ["
                            + section.getChapNo() + "주차] "
                            + section.getSectionName());
                    found = true;
                }
            }

            if (!found) {
                System.out.println("[시스템] 완료한 학습이 없습니다.");
            }

        } catch (Exception e) {
            System.out.println("[시스템] 완료 강의 조회 중 오류가 발생했습니다.");
            System.out.println(e.getMessage());
        }
    }

    private void showLoadingBar() {
        System.out.print("학습 진행 중 [");

        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("\n학습이 중단되었습니다.");
                return;
            }
            System.out.print("=");
        }

        System.out.println("]");
    }

    // 강사 - 섹션 등록
    private void createSectionProcess(long villageId, Long userId) {
        try {
            System.out.println("\n=== 새로운 강의(섹션) 업로드 ===");

            System.out.print("주차(chap_no) 입력 : ");
            int chapNo = Integer.parseInt(sc.nextLine().trim());

            System.out.print("강의 제목(section_name) 입력 : ");
            String sectionName = sc.nextLine().trim();

            System.out.print("강의 내용(content) 입력 : ");
            String content = sc.nextLine().trim();

            System.out.print("영상 링크(video_url) 입력 (선택) : ");
            String videoUrl = sc.nextLine().trim();

            if (videoUrl.isBlank()) {
                videoUrl = null;
            }

            sectionController.createSection(
                    villageId,
                    userId,
                    chapNo,
                    sectionName,
                    content,
                    videoUrl
            );

            System.out.println("[시스템] 새로운 강의가 성공적으로 업로드되었습니다.");

        } catch (NumberFormatException e) {
            System.out.println("[시스템] 주차(chap_no)는 숫자로 입력하세요.");
        } catch (Exception e) {
            System.out.println("[시스템] 섹션 등록 중 오류가 발생했습니다.");
            System.out.println(e.getMessage());
        }
    }

    // 학습 시작
    private void startLearning(long villageId) {
        try {
            List<SectionListResponse> sectionList = sectionController.displayAllSections(villageId);

            if (sectionList == null || sectionList.isEmpty()) {
                System.out.println("[시스템] 현재 등록된 강의가 없습니다.");
                return;
            }

            System.out.println("\n=== 수강 가능한 강의 목록 ===");
            for (SectionListResponse section : sectionList) {
                if (!completedSectionIds.contains(section.getSectionId())) {
                    System.out.println(section.getSectionId() + ". ["
                            + section.getChapNo() + "주차] "
                            + section.getSectionName());
                }
            }

            System.out.print("수강할 강의 번호(sectionId) 입력 : ");
            long sectionId = Long.parseLong(sc.nextLine().trim());

            boolean exists = false;
            boolean alreadyCompleted = false;

            for (SectionListResponse section : sectionList) {
                if (section.getSectionId() == sectionId) {
                    exists = true;
                    if (completedSectionIds.contains(sectionId)) {
                        alreadyCompleted = true;
                    }
                    break;
                }
            }

            if (!exists) {
                System.out.println("[시스템] 존재하지 않는 강의 번호입니다.");
                return;
            }

            if (alreadyCompleted) {
                System.out.println("[시스템] 이미 완료한 강의입니다.");
                return;
            }

            showLoadingBar();
            completedSectionIds.add(sectionId);
            System.out.println("[시스템] 학습이 완료되었습니다.");

        } catch (NumberFormatException e) {
            System.out.println("[시스템] 숫자로 입력해주세요.");
        } catch (Exception e) {
            System.out.println("[시스템] 학습 시작 중 오류가 발생했습니다.");
            System.out.println(e.getMessage());
        }
    }
}