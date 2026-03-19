package com.lms.domain.quiz.controller;

import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.quiz.service.QuizService;

import java.util.List;

public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    public List<QuizDTO> findAllQuiz() {

        return quizService.findAllQuiz();

    }

    public QuizDTO findByQuizId(int id) {

        return quizService.findByQuizId(id);
    }

}
