package com.lms.domain.quizSubmission.view;

import com.lms.domain.quizSubmission.controller.QuizSubController;

import java.util.Scanner;

public class QuizSubInputView {

    private final QuizSubController quizsubController;
    private final QuizSubOutputView quizsubOutputView;
    Scanner sc = new Scanner(System.in);

    public QuizSubInputView(QuizSubController quizsubController, QuizSubOutputView quizsubOutputView) {
        this.quizsubController = quizsubController;
        this.quizsubOutputView = quizsubOutputView;
    }

    public boolean submitAnswer(String submittedAnswer, long villageId) {
        try {
            boolean isCorrect = quizsubController.submitAnswer(submittedAnswer, villageId);
            if (isCorrect) {
                quizsubOutputView.printCorrect();
            } else {
                quizsubOutputView.printWrong();
            }
            return isCorrect;
        } catch (RuntimeException e) {
            quizsubOutputView.printError(e.getMessage());
            return false;
        }
    }

}
