package com.lms.domain.quiz.controller;

import com.lms.domain.mafia.service.MafiaService;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.quiz.dto.Requset.CreateQuizRequest;
import com.lms.domain.quiz.service.QuizService;

import java.util.List;

public class QuizController {

    private final QuizService quizService;
    private final MafiaService mafiaService;

    public QuizController(QuizService quizService, MafiaService mafiaService) {
        this.quizService = quizService;
        this.mafiaService = mafiaService;
    }

    public List<QuizDTO> findAllQuiz(long villageId) {

        return quizService.findAllQuiz(villageId);

    }

    public QuizDTO findByQuizId(long quizId, long villageId) {

        return quizService.findByQuizId(quizId, villageId);
    }

    public Long createQuiz(CreateQuizRequest request) {

        QuizDTO newQuiz = new QuizDTO(
                null,
                null,
                request.getTitle(),
                request.getVillageId(),
                request.getContent(),
                request.getAnswer());
        return quizService.createQuiz(newQuiz);
    }

    public int deleteQuiz(long quiz, long villageId) {
        return quizService.deleteQuiz(quiz, villageId);
    }

    public int updateQuiz(long quizId, String title, String content, String answer, long villageId) {
        return quizService.updateQuiz(quizId, title, content, answer, villageId);
    }

    public QuizDTO selectTodayQuiz(long villageId) {
        return quizService.selectTodayQuiz(villageId);
    }

    public Long getTodayMafiaId(long userId, long villageId) {
        return mafiaService.selectTodayMafiaId(userId, villageId);
    }

}
