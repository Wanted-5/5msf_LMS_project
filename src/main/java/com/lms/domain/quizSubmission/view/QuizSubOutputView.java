package com.lms.domain.quizSubmission.view;

public class QuizSubOutputView {

    public void printCorrect() {
        System.out.println("=== 정답입니다! ===");
    }

    public void printWrong() {
        System.out.println("=== 오답입니다. ===");
    }

    public void printError(String message) {
        System.out.println("🚨🚨" + message);
    }

}
