package com.lms.domain.quiz.controller;

import com.lms.domain.mafia.service.MafiaService;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.quiz.service.QuizService;
import com.lms.global.common.UserSession;

import java.util.List;

public class QuizController {

    private final QuizService quizService;
    private final MafiaService mafiaService;

    public QuizController(QuizService quizService, MafiaService mafiaService) {
        this.quizService = quizService;
        this.mafiaService = mafiaService;
    }

    public List<QuizDTO> findAllQuiz() {

        return quizService.findAllQuiz();

    }

    public QuizDTO findByQuizId(long quizId) {

        return quizService.findByQuizId(quizId);
    }

    public Long createQuiz(String title, String content, String answer) {

        QuizDTO newQuiz = new QuizDTO(null, null, title, content, answer);
        return quizService.createQuiz(newQuiz);
    }

    public Long deleteQuiz(long quiz) {
        return quizService.deleteQuiz(quiz);
    }

    public int updateQuiz(long quizId, String title, String content, String answer) {
        return quizService.updateQuiz(quizId, title, content, answer);
    }

    public QuizDTO selectTodayQuiz() {
        return quizService.selectTodayQuiz();
    }

    public Long getTodayMafiaId(long userId) {
        return mafiaService.selectTodayMafiaId(userId);
    }

}
