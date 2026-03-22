package com.lms.domain.quizSubmission.controller;

import com.lms.domain.quizSubmission.service.QuizSubService;

public class QuizSubController {

    private final QuizSubService quizsubService;

    public QuizSubController(QuizSubService quizsubService) {
        this.quizsubService = quizsubService;
    }
}
