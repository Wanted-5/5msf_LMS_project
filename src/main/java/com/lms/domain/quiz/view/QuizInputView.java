package com.lms.domain.quiz.view;

import com.lms.domain.quiz.controller.QuizController;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.common.UserSession;


import java.util.List;
import java.util.Scanner;

public class QuizInputView {
    private final QuizController quizController;
    private final QuizOutputview quizOutputview;
    Scanner sc = new Scanner(System.in);

    public QuizInputView(QuizController quizController, QuizOutputview quizOutputview) {
        this.quizController = quizController;
        this.quizOutputview = quizOutputview;
    }

    public void displayMainMenu() {

        System.out.println("                       ⢽⢝⡽⡺⣝⢽⢝⣞⢽⢝⡽⡺⣝⢽⢝⣞⢽⢝⡽⡺⣝⢽⡹⣝⡵⡯⣫⢯⣳⡫⡯⣫⢗⢯⡫⡯⣳⡫⡯⣫⢗⢯⡫⡯⣳⡫⡯⣫⢗⢯ ");
        System.out.println("                       ⣗⡯⣞⢽⡪⡯⡳⣝⢵⡫⣞⢽⣪⢯⢳⡳⡽⣕⢯⡫⡾⣟⢮⡳⣝⢮⡳⡳⡵⣝⣝⢮⡫⣗⢽⢝⡞⡮⡯⡺⣝⢵⡫⡯⣺⣪⡻⣪⢏⡧");
        System.out.println("                       ⢧⢳⢝⢞⣎⢏⢮⢣⡳⡹⡺⣝⢞⢮⢺⡸⡕⣟⡎⣗⢵⢝⢯⡪⡇⡯⡮⡎⣗⢝⡜⡮⡳⣕⢳⡹⡪⡳⣱⢣⡳⡝⡮⣳⡣⣫⢳⢹⢪⡣");
        System.out.println("                       ⡳⡱⡫⡪⡮⡪⡳⣕⢇⢏⢯⢮⣫⢳⢕⢵⡹⣪⢗⢽⡹⣪⢻⡎⡞⡜⣗⢝⡜⣕⢝⢎⢞⢎⢇⣗⣽⣾⣾⣵⣷⣽⢸⡪⣞⢜⢎⢧⢳⢕");
        System.out.println("                       ⡪⡪⡪⡣⡫⣪⡫⣎⢯⢹⢸⢜⢮⢣⢣⢣⣫⢪⢪⢪⢮⢳⢱⢽⣪⢪⢺⢜⢜⢜⢜⢕⢕⢝⣕⣿⣿⡿⡿⠿⢿⢿⢕⢽⢜⢜⢕⢕⢕⢕");
        System.out.println("                       ⢜⢜⢜⢜⢜⢔⢇⢇⢗⢕⠕⡝⡮⡪⡪⡪⡪⡪⡪⡪⣣⢣⢣⡹⣷⢱⢱⡣⡣⡣⡣⡣⡣⡣⣳⣿⣿⣇⣇⣏⣎⣎⣎⣧⣏⡮⡎⡎⡎⢎");
        System.out.println("                       ⡪⡂⢇⢪⠢⢣⢑⠕⡕⢜⠸⡘⢮⢊⢆⠕⡕⢕⠱⡘⡜⢔⠕⡍⡾⣇⢇⢣⠱⡘⡔⡱⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠪⡢⡃⢇");
        System.out.println("                       ⡢⡑⢅⢅⠣⡑⢅⢕⢘⢌⠪⡘⡌⢆⢅⠣⠪⡘⢌⢮⡘⢔⠱⡬⣞⢿⡌⢆⠣⡱⠨⡢⢻⣿⣿⣿⣿⣿⣿⡇⡝⡫⡩⡻⡛⢅⠣⡊⡌⠆");
        System.out.println("                       ⡊⢌⠢⢡⢑⠌⡢⢊⠔⢌⢌⠢⡊⢔⠰⢑⠅⠕⡡⢊⠻⠦⡓⠌⣾⢫⣿⢐⢑⠌⡪⢐⠅⡢⠩⣿⣿⣿⣿⣿⢓⠣⡂⡇⡊⡢⢑⠌⡢⢑");
        System.out.println("                       ⠄⢅⠊⢔⠠⡑⠄⢅⠊⠔⡠⠡⢂⠅⡊⠔⡨⠨⡐⡁⡊⢌⣂⣕⠌⡋⢦⣕⠄⠕⡠⠡⢂⠊⢔⠨⣿⣿⣿⣽⡕⣙⢄⢂⠢⠊⢄⢑⠄⠅");
        System.out.println("                       ⢑⠠⢑⠐⡐⠄⡑⠠⠡⢁⠂⠅⡂⢂⠂⠅⡂⠅⢂⢢⣾⣿⣿⣿⣵⣾⣾⣿⣎⠐⠄⡑⠄⡑⢐⢐⢸⣿⣿⣿⣶⣾⢿⠐⠨⠨⢐⠐⠨⢐");
        System.out.println("                       ⢀⠂⠂⡂⢐⠐⠠⠡⢈⠐⡈⠐⠐⠐⡈⢐⠠⠈⠄⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⡌⠐⡀⢂⠂⡁⠄⣼⣿⣿⣿⢿⠏⣂⣁⣥⣬⣔⡈⠌⡠");
        System.out.println("                       ⠄⠁⠄⠂⢈⠀⡂⠐⡀⠄⢁⠁⡁⠄⠂⠐⠈⡀⠂⡀⠄⡀⢒⠦⣽⣿⣿⣿⣷⠁⢀⠂⠠⠐⣼⣇⡏⢍⣶⣵⣿⣿⣿⣿⣿⣿⣿⣿⣿");
        System.out.println("                       ⡁⠠⠈⠀⡀⠄⠂⠀⠄⠠⠀⠠⠀⠂⠁⠠⠀⠂⠀⠐⣿⣦⣷⣿⣿⣿⣿⣿⡆⠄⠐⠀⣼⣿⢿⣳⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
        System.out.println("                       ⢀⠀⠄⠂⠀⠀⠀⠄⠠⠀⠐⠀⠠⠐⠀⠐⠀⠀⠁⠀⠹⣿⣿⣿⣿⣯⣿⣿⣿⣄⢀⣾⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀               ⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠐  ⠸⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿⡿");
        System.out.println("                                   마피아 게시판에 오신것을 환영합니다!!!");
        System.out.println("======================================================================================================================");

        while (true) {
            System.out.println("[마피아 게시판]");
            System.out.println("1. 퀴즈 전체 조회");
            System.out.println("2. 퀴즈 작성하기");
            System.out.println("3. 퀴즈 수정 및 삭제하기");
            System.out.println("4. 오늘의 퀴즈 풀기");
            System.out.println("5. 메인화면으로 나가기");
            System.out.println("번호를 입력해 주세요 : ");

            int menu = inputInt();

            switch(menu) {

                case 1:
                    backMain();
                    break;
                case 2:
                    // 마피아만 퀴즈 등록
                    // TODO : 지금은 그냥 아이디를 설정 해줬지만 실제 데이터 속에서 오늘의 마피아 찾는 코드작성

                    createQuiz();
                    break;
                case 3:
                    updateAndDelete();
                    break;
                case 4:
            }
        }

    }

    private void showAllQuiz() {
        quizOutputview.printMessage("---- 전체 퀴즈 목록 조회 ----");

        quizController.findAllQuiz();

        List<QuizDTO> quizList = quizController.findAllQuiz();

        quizOutputview.printQuiz(quizList);

    }

    // 퀴즈 상세 조회
    private void findByQuizId() {
        System.out.println("조회할 과정 번호를 입력해주세요 : ");

        int id = inputInt();

        quizController.findByQuizId(id);

        QuizDTO quiz =  quizController.findByQuizId(id);

        quizOutputview.printDetailQuiz(quiz);
    }

    // 마피아 게시판으로 돌아가기
    private void backMain() {

            showAllQuiz();

            System.out.println("1. 퀴즈 상세 조회하기 ");
            System.out.println("2. 돌아가기");
            System.out.println("메뉴를 선택해주세요 : ");

            int no = inputInt();

            if(no == 1) {
                findByQuizId();
            } else if (no == 2) {
                System.out.println("마피아 게시판으로 돌아갑니다!");
                displayMainMenu();
            } else {
                System.out.println("잘못된 입력입니다. 1번이나 2번만 눌러주세요");
            }

    }

    public void createQuiz() {

        if (UserSession.getLoggedInUser().getUserId() != 23 || UserSession.getLoggedInUser().getRole() != UserRole.INSTRUCTOR || UserSession.getLoggedInUser().getRole() != UserRole.ADMIN) {
            quizOutputview.printError("오늘의 마피아, 강사 , 관리자만이 퀴즈를 등록할 수 있습니다.");
        }else {
            System.out.println("퀴즈제목을 입력해주세요 : ");
            String title = sc.nextLine();

            System.out.println("퀴즈 내용을 입력해주새요 : ");
            String content = sc.nextLine();

            System.out.println("정답을 설정 해주세요 : ");
            String answer = sc.nextLine();


            try {
                Long result = quizController.createQuiz(title, content, answer);

                if (result != null && result > 0) {
                    quizOutputview.printSuccess("퀴즈 등록 성공 등록된 퀴즈 ID : " + result);
                } else {
                    quizOutputview.printError("과정 등록 실패");
                }
            } catch (RuntimeException e) {
                quizOutputview.printError(e.getMessage());
            }
        }

    }
    // 퀴즈 삭제
    public void deleteQuiz() {
        showAllQuiz();

        System.out.println("삭제할 퀴즈 번호를 입력해주세요 : ");
        long quiz = inputLong();

        try {
            Long result = quizController.deleteQuiz(quiz);

            if (result != null && result >= 0) {
                quizOutputview.printSuccess("퀴즈가 삭제 되었습니다");
                QuizDTO deleteQuiz = quizController.findByQuizId((int) quiz);

                if (deleteQuiz == null) {
                    quizOutputview.printMessage("확인 : " + quiz + "번 퀴즈가 정상 삭제 되었습니다");
                } else {
                    quizOutputview.printError("삭제 확인 중 문제 발생 !!");
                }
            }
        } catch (RuntimeException e) {
            quizOutputview.printError(e.getMessage());
        }
    }
    // 퀴즈 수정 로직
    public void updateQuiz() {
        showAllQuiz();
        System.out.println("수정할 퀴즈 번호를 입력해주세요 : ");
        long quizId = inputLong();

        System.out.println("수정할 제목을 입력해주세요 : ");
        String title = sc.nextLine();

        System.out.println("수정할 내용을 입력해주세요 : ");
        String content = sc.nextLine();

        System.out.println("수정할 정답을 입력해주세요 : ");
        String answer = sc.nextLine();

        try {
            Long result = quizController.updateQuiz(quizId, title, content, answer);

            if (result != null && result >= 0) {
                quizOutputview.printSuccess("퀴즈가 수정 되었습니다");
                QuizDTO updateQuiz = quizController.findByQuizId((int) quizId);

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

    private void updateAndDelete() {

        if (UserSession.getLoggedInUser().getUserId() != 23 || UserSession.getLoggedInUser().getRole() != UserRole.INSTRUCTOR || UserSession.getLoggedInUser().getRole() != UserRole.ADMIN) {
            quizOutputview.printError("오늘의 마피아, 강사, 관리자만이 퀴즈를 수정 및 삭제 할 수 있습니다.");
        } else {
            System.out.println("1. 퀴즈 수정하기");
            System.out.println("2. 퀴즈 삭제하기");
            System.out.println("3. 마피아 게시판으로 돌아가기");
            System.out.println("번호를 입력해주세요");

            int no = inputInt();

            if (no == 1) {
                updateQuiz();
            } else if (no == 2) {
                deleteQuiz();
            } else if (no == 3) {
                displayMainMenu();
            } else {
                System.out.println("없는 번호입니다 다시 입력해주세요");
            }
        }
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
