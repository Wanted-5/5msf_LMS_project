package com.lms.domain.section.view;

import com.lms.domain.learning.controller.LearningController;
import com.lms.domain.section.controller.SectionController;
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
}
