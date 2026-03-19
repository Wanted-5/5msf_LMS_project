package com.lms.domain.quiz.view;

import com.lms.domain.quiz.controller.QuizController;
import com.lms.domain.quiz.dto.QuizDTO;


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
            System.out.println("3. 오늘의 퀴즈 풀기");
            System.out.println("4. 이전으로");
            System.out.println("번호를 입력해 주세요 : ");

            int menu = inputInt();

            switch(menu) {

                case 1:
                    backMain();
                    break;
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
}
