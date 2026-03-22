package com.lms.domain.quiz.controller;

import com.lms.domain.mafia.service.MafiaService;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.quiz.service.QuizService;

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

    public QuizDTO findByQuizId(long id) {

        return quizService.findByQuizId(id);
    }

    public Long createQuiz(String title, String content, String answer) {
        Long userId = 23L;  // 더미 user_id (오늘 마피아로 뽑힌 사람)
        Long mafiaId = mafiaService.selectTodayMafiaId(userId);


        QuizDTO newQuiz = new QuizDTO(null, mafiaId, title, content, answer);
        return quizService.createQuiz(newQuiz);
    }

    public Long deleteQuiz(long quiz) {
        return quizService.deleteQuiz(quiz);
    }

    public Long updateQuiz(long quizId, String title, String content, String answer) {
        return quizService.updateQuiz(quizId, title, content, answer);
    }

}
