package com.lms.domain.section.view;


import com.lms.domain.learning.controller.LearningController;
import com.lms.domain.learning.dto.LearningDTO;
import com.lms.domain.learning.dto.LearningStatus;
import com.lms.domain.learning.dto.reseponse.LearningSectionResponse;
import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.dto.SectionDTO;
import com.lms.domain.section.dto.response.SectionDetailResponse;
import com.lms.domain.section.dto.response.SectionListResponse;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;
import com.lms.global.util.PasswordUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SectionInputView {

    private final SectionController sectionController;
    private final SectionOutputView sectionOutputView;
    private final LearningController learningController;

    public SectionInputView(SectionController sectionController, SectionOutputView sectionOutputView, LearningController learningController) {
        this.sectionController = sectionController;
        this.sectionOutputView = sectionOutputView;
        this.learningController = learningController;
    }

    private final Scanner sc = new Scanner(System.in);

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
            System.out.println("      [ 3 ] 🔎 수강 상태별 강의 목록 보기");
            System.out.println("      [ 0 ] 🚪 교육센터 퇴장 (마을 광장으로 돌아가기)");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 메뉴의 번호를 입력해주세요 : ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n  [ 시스템 ] 📚 수강 가능한 강의 목록을 탐색합니다...");
                    showAllSectionsProcess(villageId);
                    // TODO: controller.showUncompletedSections(villageId);
                    break;
                case "2":
                    System.out.println("\n  [ 시스템 ] ▶️ 학습 시스템을 가동합니다...");
                    startLearning(villageId);
                    // TODO: controller.startLearning(villageId);
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

    // 강의 전체 조회
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

    // 2. 학습 시작
    // TODO : 강의 전체 조회 먼저 해주기 -> chap_no 받아서 villageId랑 같이 전달하면서 상세 조회 구현
    // TODO : 상세 조회 된 상태에서 수강하기 누르면 수강시작.
    private void startLearning(long villageId) {

        long currentUserId = UserSession.getLoggedInUser().getUserId();

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ▶️ 학습 시스템 (강의 상세 및 수강)               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        List<SectionListResponse> sectionList = sectionController.displayAllSections(villageId);
        if (sectionList != null && !sectionList.isEmpty()) {
            sectionOutputView.displaySectionList(sectionList);
        }

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 상세 조회할 강의 번호(ID)를 입력하세요 (취소는 '0') : ");

        long sectionId;
        try {
            sectionId = Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\n  🚨 [오류] 강의 번호는 숫자만 입력 가능합니다.");
            return;
        }

        if (sectionId == 0) {
            System.out.println("\n  [ 시스템 ] 학습을 취소하고 교육센터 메인으로 돌아갑니다.");
            return;
        }

        try {
            SectionDetailResponse response = sectionController.displaySectionDetail(villageId, sectionId);

            sectionOutputView.displaySectionDetail(response);

            System.out.println("\n  🚨 [확인] 위 강의를 지금 바로 수강하시겠습니까?");
            System.out.print("  ▶ 수강을 시작하려면 대문자 'Y'를 입력해 주세요 (취소는 아무 키) : ");

            if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
                System.out.println("\n  [ 시스템 ] 강의 영상 재생을 준비합니다...");

                showLoadingBar();

                /* =========================================================================
                   TODO: [추후 DB 연동 예정]
                   learning_history 테이블이 추가되면, 여기에 수강 완료(INSERT) 로직 추가
                   long currentUserId = UserSession.getLoggedInUser().getUserId();
                   sectionController.completeSection(currentUserId, sectionId);
                   ========================================================================= */

                learningController.updateStatusBeforeCompleted(currentUserId, sectionId);

                System.out.println("\n  🎉 [ 시스템 ] 수강 완료 처리가 되었습니다! 고생하셨습니다.");
            } else {
                System.out.println("\n  [ 시스템 ] 수강을 취소했습니다. 교육센터 메인으로 돌아갑니다.");
            }

        } catch (Exception e) {
            sectionOutputView.displayFailure(e.getMessage());
        }
    }

    // 3번 수강 상태별 강의 목록 보기
    private void showCompletedSections(long villageId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 📊 수강 상태별 강의 목록 조회                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        LearningStatus selectedStatus = null;
        String statusDisplayName = "";

        while (true) {
            System.out.println("  [ 1 ] ⏳ 수강 전 (BEFORE_LEARNING)");
            System.out.println("  [ 2 ] ▶️ 수강 중 (IN_PROGRESS)");
            System.out.println("  [ 3 ] ✅ 수강 완료 (COMPLETED)");
            System.out.println("  [ 0 ] 🔙 이전 메뉴로 돌아가기");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 조회할 상태의 번호를 선택해 주세요 : ");

            String choice = sc.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println("  [시스템] 이전 메뉴로 돌아갑니다.");
                return;
            } else if (choice.equals("1")) {
                selectedStatus = LearningStatus.BEFORE_LEARNING;
                statusDisplayName = "⏳ 수강 전";
                break;
            } else if (choice.equals("2")) {
                selectedStatus = LearningStatus.IN_PROGRESS;
                statusDisplayName = "▶️ 수강 중";
                break;
            } else if (choice.equals("3")) {
                selectedStatus = LearningStatus.COMPLETED;
                statusDisplayName = "✅ 수강 완료";
                break;
            } else {
                System.out.println("\n  🚨 [오류] 올바른 번호(0~3)를 입력해 주세요.\n");
            }
        }

        long currentUserId = UserSession.getLoggedInUser().getUserId();
        System.out.println("\n  [시스템] [" + statusDisplayName + "] 상태의 강의를 불러옵니다...");

        try {
            List<LearningSectionResponse> learningDTOList = learningController.findLearningDetailsByStatus(
                    currentUserId, villageId, selectedStatus);

            if (learningDTOList == null || learningDTOList.isEmpty()) {
                System.out.println("────────────────────────────────────────────────────────────────");
                System.out.println("  [시스템] 현재 [" + statusDisplayName + "] 상태인 강의가 없습니다.");
                System.out.println("────────────────────────────────────────────────────────────────\n");
                return;
            }

            sectionOutputView.displaySectionsByStatus(learningDTOList, statusDisplayName);

        } catch (Exception e) {
            sectionOutputView.displayFailure(e.getMessage());
        }
    }

    // comment, 강사용 메뉴 시작

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


    // ============== 내부 편의 메서드 ===========================
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

