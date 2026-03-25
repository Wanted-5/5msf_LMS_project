package com.lms.domain.quiz.view;

import com.lms.domain.quiz.controller.QuizController;
import com.lms.domain.quiz.dto.QuizDTO;


import com.lms.domain.quiz.dto.Requset.CreateQuizRequest;
import com.lms.domain.quizSubmission.view.QuizSubInputView;

import com.lms.domain.users.dto.UserRole;
import com.lms.global.AppContext.AppContext;
import com.lms.global.common.UserSession;


import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class QuizInputView {
    private final QuizController quizController;
    private final QuizOutputview quizOutputview;
    private final QuizSubInputView quizSubInputView;
    Scanner sc = new Scanner(System.in);

    public QuizInputView(QuizController quizController, QuizOutputview quizOutputview, QuizSubInputView quizSubInputView) {
        this.quizController = quizController;
        this.quizOutputview = quizOutputview;
        this.quizSubInputView = quizSubInputView;
    }

    public void displayMainMenu(long villageId) {

        System.out.println("⢽⢝⡽⡺⣝⢽⢝⣞⢽⢝⡽⡺⣝⢽⢝⣞⢽⢝⡽⡺⣝⢽⡹⣝⡵⡯⣫⢯⣳⡫⡯⣫⢗⢯⡫⡯⣳⡫⡯⣫⢗⢯⡫⡯⣳⡫⡯⣫⢗⢯ ");
        System.out.println("⣗⡯⣞⢽⡪⡯⡳⣝⢵⡫⣞⢽⣪⢯⢳⡳⡽⣕⢯⡫⡾⣟⢮⡳⣝⢮⡳⡳⡵⣝⣝⢮⡫⣗⢽⢝⡞⡮⡯⡺⣝⢵⡫⡯⣺⣪⡻⣪⢏⡧");
        System.out.println("⢧⢳⢝⢞⣎⢏⢮⢣⡳⡹⡺⣝⢞⢮⢺⡸⡕⣟⡎⣗⢵⢝⢯⡪⡇⡯⡮⡎⣗⢝⡜⡮⡳⣕⢳⡹⡪⡳⣱⢣⡳⡝⡮⣳⡣⣫⢳⢹⢪⡣");
        System.out.println("⡳⡱⡫⡪⡮⡪⡳⣕⢇⢏⢯⢮⣫⢳⢕⢵⡹⣪⢗⢽⡹⣪⢻⡎⡞⡜⣗⢝⡜⣕⢝⢎⢞⢎⢇⣗⣽⣾⣾⣵⣷⣽⢸⡪⣞⢜⢎⢧⢳⢕");
        System.out.println("⡪⡪⡪⡣⡫⣪⡫⣎⢯⢹⢸⢜⢮⢣⢣⢣⣫⢪⢪⢪⢮⢳⢱⢽⣪⢪⢺⢜⢜⢜⢜⢕⢕⢝⣕⣿⣿⡿⡿⠿⢿⢿⢕⢽⢜⢜⢕⢕⢕⢕");
        System.out.println("⢜⢜⢜⢜⢜⢔⢇⢇⢗⢕⠕⡝⡮⡪⡪⡪⡪⡪⡪⡪⣣⢣⢣⡹⣷⢱⢱⡣⡣⡣⡣⡣⡣⡣⣳⣿⣿⣇⣇⣏⣎⣎⣎⣧⣏⡮⡎⡎⡎⢎");
        System.out.println("⡪⡂⢇⢪⠢⢣⢑⠕⡕⢜⠸⡘⢮⢊⢆⠕⡕⢕⠱⡘⡜⢔⠕⡍⡾⣇⢇⢣⠱⡘⡔⡱⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠪⡢⡃⢇");
        System.out.println("⡢⡑⢅⢅⠣⡑⢅⢕⢘⢌⠪⡘⡌⢆⢅⠣⠪⡘⢌⢮⡘⢔⠱⡬⣞⢿⡌⢆⠣⡱⠨⡢⢻⣿⣿⣿⣿⣿⣿⡇⡝⡫⡩⡻⡛⢅⠣⡊⡌⠆");
        System.out.println("⡊⢌⠢⢡⢑⠌⡢⢊⠔⢌⢌⠢⡊⢔⠰⢑⠅⠕⡡⢊⠻⠦⡓⠌⣾⢫⣿⢐⢑⠌⡪⢐⠅⡢⠩⣿⣿⣿⣿⣿⢓⠣⡂⡇⡊⡢⢑⠌⡢⢑");
        System.out.println("⠄⢅⠊⢔⠠⡑⠄⢅⠊⠔⡠⠡⢂⠅⡊⠔⡨⠨⡐⡁⡊⢌⣂⣕⠌⡋⢦⣕⠄⠕⡠⠡⢂⠊⢔⠨⣿⣿⣿⣽⡕⣙⢄⢂⠢⠊⢄⢑⠄⠅");
        System.out.println("⢑⠠⢑⠐⡐⠄⡑⠠⠡⢁⠂⠅⡂⢂⠂⠅⡂⠅⢂⢢⣾⣿⣿⣿⣵⣾⣾⣿⣎⠐⠄⡑⠄⡑⢐⢐⢸⣿⣿⣿⣶⣾⢿⠐⠨⠨⢐⠐⠨⢐");
        System.out.println("⢀⠂⠂⡂⢐⠐⠠⠡⢈⠐⡈⠐⠐⠐⡈⢐⠠⠈⠄⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⡌⠐⡀⢂⠂⡁⠄⣼⣿⣿⣿⢿⠏⣂⣁⣥⣬⣔⡈⠌⡠");
        System.out.println("⠄⠁⠄⠂⢈⠀⡂⠐⡀⠄⢁⠁⡁⠄⠂⠐⠈⡀⠂⡀⠄⡀⢒⠦⣽⣿⣿⣿⣷⠁⢀⠂⠠⠐⣼⣇⡏⢍⣶⣵⣿⣿⣿⣿⣿⣿⣿⣿⣿");
        System.out.println("⡁⠠⠈⠀⡀⠄⠂⠀⠄⠠⠀⠠⠀⠂⠁⠠⠀⠂⠀⠐⣿⣦⣷⣿⣿⣿⣿⣿⡆⠄⠐⠀⣼⣿⢿⣳⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
        System.out.println("⢀⠀⠄⠂⠀⠀⠀⠄⠠⠀⠐⠀⠠⠐⠀⠐⠀⠀⠁⠀⠹⣿⣿⣿⣿⣯⣿⣿⣿⣄⢀⣾⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
        System.out.println("⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠐  ⠸⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿");
        System.out.println(" ╠══════════════════════════════════════════════════════════════╣ ╠══════════════════════════════════════════════════════════════╣");

        System.out.println(" ╔══════════════════════════════════════════════════════════════╗");
        System.out.println(" ║                                                              ║");
        System.out.println(" ║            ███╗   ███╗ █████╗ ███████╗██╗ █████╗             ║");
        System.out.println(" ║            ████╗ ████║██╔══██╗██╔════╝██║██╔══██╗            ║");
        System.out.println(" ║            ██╔████╔██║███████║█████╗  ██║███████║            ║");
        System.out.println(" ║            ██║╚██╔╝██║██╔══██║██╔══╝  ██║██╔══██║            ║");
        System.out.println(" ║            ██║ ╚═╝ ██║██║  ██║██║     ██║██║  ██║            ║");
        System.out.println(" ║            ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝            ║");
        System.out.println(" ║                                                              ║");
        System.out.println(" ╠══════════════════════════════════════════════════════════════╣");
        System.out.println(" ║   🕯️  어둠 속에서 마피아가 당신을 지켜보고 있습니다...  🕯️          ║");
        System.out.println(" ║   💀  오늘의 희생자는 누구인가...                        💀      ║");
        System.out.println(" ╠══════════════════════════════════════════════════════════════╣");

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🕵️ 마피아 게임 (코드 퀴즈) 관제실               ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 이곳은 밤이 되면 누군가 퀴즈를 남기고 가는 곳입니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ]  📋 퀴즈 전체 리스트 조회");
            System.out.println("      [ 2 ]  🩸 새로운 퀴즈 기획 및 작성");
            System.out.println("      [ 3 ]  🔪 퀴즈 관리 (수정/삭제) [권한 제한]");
            System.out.println("      [ 4 ]  🎭 오늘의 퀴즈 풀기");
            System.out.println("      [ 5 ]  🔎 오늘의 마피아 조회");
            System.out.println("      [ 0 ]  🚪 마을 광장으로 돌아가기");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 요원의 다음 행동을 선택하세요 : ");

            int menu = inputInt();

            switch(menu) {

                case 1:
                    backMain(villageId);
                    break;
                case 2:
                    createQuiz(villageId);
                    break;
                case 3:
                    updateAndDelete(villageId);
                    break;
                case 4:
                    selectTodayQuiz(villageId);
                    break;
                case 5:
                    AppContext.getAppContext().mafiaAppContext.mafiaInputView.selectTodayMafiaInfo(villageId);
                    break;
                case 0:
                    //TODO : 테스트 해보기
                    return;
            }
        }

    }

    private void showAllQuiz(long villageId) {
        quizOutputview.printMessage("전체 퀴즈 목록 조회");

        List<QuizDTO> quizList = quizController.findAllQuiz(villageId);

        quizOutputview.printQuiz(quizList);

    }

    // 퀴즈 상세 조회
    private void findByQuizId(long villageId) {
        System.out.println("🔪 조회할 퀴즈 번호를 입력하시오... : ");

        int id = inputInt();

        quizController.findByQuizId(id, villageId);

        QuizDTO quiz =  quizController.findByQuizId(id, villageId);

        quizOutputview.printDetailQuiz(quiz);
    }

    // 마피아 게시판으로 돌아가기
    private void backMain(long villageId) {

            showAllQuiz(villageId);

        System.out.println("  🩸 [ 1 ]  퀴즈 상세 조회하기");
        System.out.println("  🚪 [ 2 ]  돌아가기");
        System.out.println("  ─────────────────────────────────────");
        System.out.print("  ▶ 선택하세요 : ");

            int no = inputInt();

            if(no == 1) {
                findByQuizId(villageId);
            } else if (no == 2) {
                System.out.println("  🚪 마피아 게시판으로 돌아갑니다...");
                displayMainMenu(villageId);
            } else {
                System.out.println("  ⚠️  잘못된 입력입니다. 1번이나 2번만 눌러주세요.");
            }

    }

    private void createQuiz(long villageId) {

        UserRole role = UserSession.getLoggedInUser().getRole();
        Long userId = UserSession.getLoggedInUser().getUserId();

        boolean isMafia = false;
        try {
            quizController.getTodayMafiaId(userId.intValue(), villageId);
            isMafia = true;
        } catch (RuntimeException e) {
            isMafia = false;
        }

        boolean hasPermission = role == UserRole.INSTRUCTOR || role == UserRole.ADMIN || isMafia;

        if (!hasPermission) {
            quizOutputview.printError("오늘의 마피아, 강사, 관리자만이 퀴즈를 등록할 수 있습니다.");
            return;
        }
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("           🩸 퀴즈 작성하기 🩸               ");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.print("  📜 퀴즈 제목을 입력하시오... : ");
        String title = sc.nextLine();

        System.out.print("  🔪 퀴즈 내용을 입력하시오... : ");
        String content = sc.nextLine();

        System.out.print("  👁️  1번 보기를 입력하시오... : ");
        String option1 = sc.nextLine();

        System.out.print("  👁️  2번 보기를 입력하시오... : ");
        String option2 = sc.nextLine();

        System.out.print("  👁️  3번 보기를 입력하시오... : ");
        String option3 = sc.nextLine();

        System.out.print("  👁️  4번 보기를 입력하시오... : ");
        String option4 = sc.nextLine();

        String fullContent = content + "\n1. " + option1 + "\n2. " + option2 + "\n3. " + option3 + "\n4. " + option4;

        System.out.print("  🩸 정답 번호를 설정하시오... : ");
        String answer = sc.nextLine();

            try {
                CreateQuizRequest request = new CreateQuizRequest(villageId, title, fullContent, answer);
                Long result = quizController.createQuiz(request);



                if (result != null && result > 0) {
                    quizOutputview.printSuccess("🩸 퀴즈 등록 완료... 등록된 퀴즈 ID : " + result + " 🩸");
                } else {
                    quizOutputview.printError("과정 등록 실패");
                }
            } catch (RuntimeException e) {
                quizOutputview.printError(e.getMessage());
            }
    }

    // 퀴즈 삭제
    private void deleteQuiz(long villageId) {
        showAllQuiz(villageId);

        System.out.println("  🔪 삭제할 퀴즈 번호를 입력하시오... : ");
        long quizId = inputLong();

        try {
            int result = quizController.deleteQuiz(quizId, villageId);

            if (result != 0) {
                quizOutputview.printSuccess("🩸 퀴즈가 어둠 속으로 사라졌습니다...");
                quizOutputview.printMessage("확인 : " + quizId + "번 퀴즈가 정상 삭제 되었습니다");
            } else {
                quizOutputview.printError("수정 확인 중 문제 발생 !!");
            }
        } catch (RuntimeException e) {
            quizOutputview.printError(e.getMessage());
        }
    }

    // 퀴즈 수정 로직
    private void updateQuiz(long villageId) {
        showAllQuiz(villageId);
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("           🩸 퀴즈 수정하기 🩸               ");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.print("  🔪 수정할 퀴즈 번호를 입력하시오... : ");
        long quizId = inputLong();

        System.out.print("  📜 수정할 제목을 입력하시오... : ");
        String title = sc.nextLine();

        System.out.print("  🔪 수정할 내용을 입력하시오... : ");
        String content = sc.nextLine();

        System.out.print("  👁️  1번 보기를 수정하시오... : ");
        String option1 = sc.nextLine();

        System.out.print("  👁️  2번 보기를 수정하시오... : ");
        String option2 = sc.nextLine();

        System.out.print("  👁️  3번 보기를 수정하시오... : ");
        String option3 = sc.nextLine();

        System.out.print("  👁️  4번 보기를 수정하시오... : ");
        String option4 = sc.nextLine();

        String fullContent = content + "\n1. " + option1 + "\n2. " + option2 + "\n3. " + option3 + "\n4. " + option4;

        System.out.print("  🩸 수정할 정답 번호를 입력하시오... : ");
        String answer = sc.nextLine();
        try {
            int result = quizController.updateQuiz(quizId, title, fullContent, answer, villageId);

            if (result != 0) {
                quizOutputview.printSuccess("🩸 퀴즈가 어둠 속에서 변형되었습니다...");
                QuizDTO updateQuiz = quizController.findByQuizId(quizId, villageId);

                if (updateQuiz != null) {
                    quizOutputview.printMessage("확인 : " + quizId + "번 퀴즈가 정상 수정 되었습니다");
                } else {
                    quizOutputview.printError("수정 확인 중 문제 발생 !!");
                }
            }
        } catch (RuntimeException e) {
            quizOutputview.printError(e.getMessage());
        }
    }

    private void updateAndDelete(long villageId) {

        UserRole role = UserSession.getLoggedInUser().getRole();
        Long userId = UserSession.getLoggedInUser().getUserId();

        boolean isMafia = false;
        try {
            quizController.getTodayMafiaId(userId.intValue(), villageId);
            isMafia = true;
        } catch (RuntimeException e) {
            isMafia = false;
        }

        boolean hasPermission = role == UserRole.INSTRUCTOR || role == UserRole.ADMIN || isMafia;

        if (!hasPermission) {
            quizOutputview.printError("오늘의 마피아, 강사, 관리자만이 퀴즈를 수정 및 삭제 할 수 있습니다.");
            return;
        }
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("        🩸 퀴즈 수정 및 삭제 🩸               ");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("  🔪 [ 1 ]  퀴즈 수정하기");
        System.out.println("  💀 [ 2 ]  퀴즈 삭제하기");
        System.out.println("  🚪 [ 3 ]  마피아 게시판으로 돌아가기");
        System.out.println("  ─────────────────────────────────────");
        System.out.print("  ▶ 선택하세요 : ");

            int no = inputInt();

            if (no == 1) {
                updateQuiz(villageId);
            } else if (no == 2) {
                deleteQuiz(villageId);
            } else if (no == 3) {
                displayMainMenu(villageId);
            } else {
                System.out.println("없는 번호입니다 다시 입력해주세요");
            }
    }

    // 오늘의 퀴즈 풀기
    private void selectTodayQuiz(long villageId) {
        try {

            if(!isBeforeDeadline()) {
                quizOutputview.printError("🕯️  시간이 지났습니다... 오늘의 퀴즈에 입장할 수 없습니다... 🕯️");
                return;
            }

            QuizDTO quiz = quizController.selectTodayQuiz(villageId);

            System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
            System.out.println("           🩸 오늘의 퀴즈 🩸                  ");
            System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
            System.out.println("  📜 제목  : " + quiz.getTitle());
            System.out.println("  🔪 내용  : " + quiz.getContent());
            System.out.println("  ─────────────────────────────────────");
            System.out.println("  👁️  [ 1 ]  퀴즈 풀이 시작");
            System.out.println("  🚪 [ 2 ]  돌아가기");
            System.out.print("  ▶ 선택하세요 : ");
            int menu = inputInt();

            if (menu == 1) {
                System.out.print("  🩸 정답을 입력하시오... : ");
                int answer = inputInt();

                if(!isBeforeDeadline()) {
                    quizOutputview.printError("🕯️  시간이 지났습니다... 제출이 불가합니다... 🕯️");
                    return;
                }
                quizSubInputView.submitAnswer(String.valueOf(answer), villageId);
            } else {
                displayMainMenu(villageId);
            }
        } catch (RuntimeException e) {
            quizOutputview.printError(e.getMessage());
        }
    }

    private boolean isBeforeDeadline() {
        LocalTime now = LocalTime.now();
        LocalTime deadline = LocalTime.of(23, 59, 59);
        return now.isBefore(deadline);
    }

    // Scanner 담아둠
    private int inputInt() {

        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요 : ");
            }
        }

    }

    private long inputLong() {
        while (true) {
            try {
                long value = Long.parseLong(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}
