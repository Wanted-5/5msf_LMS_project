package com.lms.domain.quiz.view;

import com.lms.domain.quiz.dto.QuizDTO;

import java.util.List;

public class QuizOutputview {

    public void printMessage(String s) {

    }

    public void printError(String message) {
        System.out.println("🚨🚨" + message);
    }

    public void printQuiz(List<QuizDTO> quizList) {

        if(quizList == null || quizList.isEmpty()) {
            System.out.println("조회된 퀴즈가 없습니다");
            return;
        }

        System.out.println("============퀴즈 전체 목록 조회 결과==========");
        for(QuizDTO quizDTO : quizList) {
            System.out.println("퀴즈번호 : " + quizDTO.getQuizId());
            System.out.println("퀴즈제목 : " + quizDTO.getTitle());
        }
    }

    public void printDetailQuiz(QuizDTO quiz) {

        if(quiz == null ) {
            System.out.println("상세 조회 된 퀴즈가 없습니다");
            return;
        }


        System.out.println("=======퀴즈 상세 조회 결과=========");
        System.out.println("제목: " + quiz.getTitle());
        System.out.println("내용: " + quiz.getContent());

    }

    public void printSuccess(String message) {

        System.out.println("✅" + message);

    }
}
