package com.lms.domain.section.view;


import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.DTO.SectionDTO;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.view.VillageOutputView;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SectionInputView {

    private final SectionController sectionController;
    private final Scanner sc = new Scanner(System.in);
    private final SectionOutputView sectionOutputView;
    // 현재 실행 중인 동안만 완료 강의 저장
    private final Set<Long> completedSectionIds = new HashSet<>();

    public SectionInputView(SectionController sectionController, SectionOutputView sectionOutputView) {
        this.sectionController = sectionController;
        this.sectionOutputView = sectionOutputView;
    }

    public void displayStudentSectionMenu(VillageDTO village) {
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
    /* ------------------------강사 파트 ---------------------------*/
    public void displayInstructorSectionMenu(Long villageId) {
        while (true) {
            System.out.println("\n===> 4. 마을 메인페이지 입장 (강사 모드)");
            System.out.println("=== 메인 콘솔 (초기 화면 - 강사) ===");
            System.out.println("1. 출석 체크 및 관리");
            System.out.println("2. 교육센터 관리");
            System.out.println("3. 게시판 관리");
            System.out.println("4. 마피아 게시판 관리");
            System.out.println("5. 숙소(마이페이지)로 이동");
            System.out.println("6. 수강생 관리");
            System.out.println("7. 오늘의 마피아 뽑기");
            System.out.println("8. 로그아웃");
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
                    System.out.println("출석 체크 및 관리는 아직 구현되지 않았습니다.");
                    break;
                case 2:
                    displayInstructorEducationMenu(villageId);
                    break;
                case 3:
                    System.out.println("게시판 관리는 아직 구현되지 않았습니다.");
                    break;
                case 4:
                    System.out.println("마피아 게시판 관리는 아직 구현되지 않았습니다.");
                    break;
                case 5:
                    System.out.println("숙소(마이페이지) 기능은 아직 구현되지 않았습니다.");
                    break;
                case 6:
                    System.out.println("수강생 관리 기능은 아직 구현되지 않았습니다.");
                    break;
                case 7:
                    System.out.println("오늘의 마피아 뽑기 기능은 아직 구현되지 않았습니다.");
                    break;
                case 8:
                    System.out.println("로그아웃합니다.");
                    return;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }
    public void displayInstructorEducationMenu(Long villageId) {
        while (true) {
            System.out.println("\n===> 6. 교육센터 관리");
            System.out.println("1. 전체 강의 보기");
            System.out.println("2. 학습 시작하기");
            System.out.println("3. 완료한 학습 목록 보기");
            System.out.println("4. 신규 섹션 업로드");
            System.out.println("5. 메인페이지로 돌아가기");
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
                    showAllSections(villageId);
                    break;
                case 2:
                    startLearning(villageId);
                    break;
                case 3:
                    showCompletedSections(villageId);
                    break;
                case 4:
                    uploadNewSection(villageId);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }
    private void showAllSections(Long villageId) {
        try {
            sectionOutputView.printSectionListHeader();
            List<SectionDTO> sectionList = sectionController.findSectionsByVillageId(villageId);
            sectionOutputView.printSectionList(sectionList);
        } catch (Exception e) {
            sectionOutputView.printErrorMessage(e.getMessage());
        }
    }

    private void uploadNewSection(Long villageId) {
        try {
            System.out.println("\n=== 새로운 강의(섹션) 업로드를 시작합니다. ===");

            System.out.print("- 주차(chap_no) 입력 : ");
            int chapNo = Integer.parseInt(sc.nextLine());

            System.out.print("- 강의 제목(section_name) 입력 : ");
            String sectionName = sc.nextLine();

            System.out.print("- 강의 내용(content) 입력 : ");
            String content = sc.nextLine();

            System.out.print("- 영상 링크(video_url) 입력 (선택) : ");
            String videoUrl = sc.nextLine();

            if (videoUrl == null || videoUrl.trim().isEmpty()) {
                videoUrl = null;
            }

            sectionController.createSection(villageId, chapNo, sectionName, content, videoUrl);

            System.out.println("=== 새로운 강의가 성공적으로 업로드되었습니다. ===");

        } catch (NumberFormatException e) {
            System.out.println("주차 번호는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println("섹션 업로드 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
