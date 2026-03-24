package com.lms.domain.section.view;

import com.lms.domain.learning.controller.LearningController;
import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.dto.request.CreateSectionRequest;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;

import java.util.Scanner;

public class InstructorSectionInputView {

    private final SectionController sectionController;
    private final SectionOutputView sectionOutputView;
    private final LearningController learningController;

    private final Scanner sc = new Scanner(System.in);

    public InstructorSectionInputView(SectionController sectionController, SectionOutputView sectionOutputView, LearningController learningController) {
        this.sectionController = sectionController;
        this.sectionOutputView = sectionOutputView;
        this.learningController = learningController;
    }

    // comment, 강사용 메뉴 시작

    // 강사용 메뉴
    public void displayInstructorSectionMenu(long villageId) {

        long currentUser = UserSession.getLoggedInUser().getUserId();
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                  🎓 교육센터 운영 패널 (강사)                   ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 강의 진행 현황과 섹션 관리를 여기서 수행합니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📚 전체 강의 목록 조회");
            System.out.println("      [ 2 ] ▶️ 학습 시작 / 테스트");
            System.out.println("      [ 3 ] 🔎 수강 상태별 강의 목록 보기");
            System.out.println("      [ 4 ] 🛠 신규 섹션 업로드");
            System.out.println("      [ 5 ] ↩ 메인페이지 복귀");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 번호를 입력하세요 : ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    AppContext.getAppContext().sectionAppContext.studentSectionInputView.showAllSectionsProcess(villageId);
                    break;
                case "2":
                    AppContext.getAppContext().sectionAppContext.studentSectionInputView.startLearning(villageId);
                    break;
                case "3":
                    AppContext.getAppContext().sectionAppContext.studentSectionInputView.showCompletedSections(villageId);
                    break;
                case "4":
                    createSectionProcess(villageId, currentUser);
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
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🎓 신규 강의(Section) 업로드                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 마을 주민들에게 공유할 새로운 교육 콘텐츠를 등록합니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        try {
            System.out.print("  ▶ [ 필수 ] 주차 (숫자만 입력, 예: 1) : ");
            int chapNo = Integer.parseInt(sc.nextLine().trim());

            System.out.print("  ▶ [ 필수 ] 강의 제목 : ");
            String sectionName = sc.nextLine().trim();
            if (sectionName.isEmpty()) throw new IllegalArgumentException("강의 제목은 필수입니다.");

            System.out.print("  ▶ [ 필수 ] 강의 상세 설명 : ");
            String content = sc.nextLine().trim();

            System.out.print("  ▶ [ 선택 ] 영상 링크 (URL 미입력 시 엔터) : ");
            String videoUrl = sc.nextLine().trim();
            if (videoUrl.isBlank()) videoUrl = null;

            System.out.println("\n  [ 시스템 ] 강의 데이터를 검증하고 서버에 등록 중입니다...");

            // 1. DTO 조립
            CreateSectionRequest request = new CreateSectionRequest(
                    villageId,
                    userId,
                    chapNo,
                    sectionName,
                    content,
                    videoUrl
            );

            // 강의 생성
            Long newSectionId = sectionController.createSection(request);


            // TODO : 만약 마을에 유저가 있으면
            // 수강 등록 성공 -> Learning_history에 해당하는 마을의 모든 유저 수강전으로 등록
            int studentCount = learningController.insertIntoBeforeLearning(newSectionId, villageId);

            sectionOutputView.displayCreateSectionSuccess(studentCount);

        } catch (NumberFormatException e) {
            sectionOutputView.displaySectionError("주차(Chapter)는 숫자만 입력 가능합니다.");
        } catch (IllegalArgumentException e) {
            sectionOutputView.displaySectionError(e.getMessage());
        } catch (Exception e) {
            sectionOutputView.displaySectionError("예상치 못한 서버 오류: " + e.getMessage());
        }
    }
}
