package com.lms.global.AppContext;

import com.lms.domain.mafia.service.MafiaService;
import com.lms.domain.quiz.controller.QuizController;
import com.lms.domain.quiz.service.QuizService;
import com.lms.domain.quiz.view.QuizInputView;
import com.lms.domain.quiz.view.QuizOutputview;

import java.sql.Connection;

public class QuizAppContext {


    public final QuizInputView quizInputView;

    public QuizAppContext(Connection con) {

        QuizService quizService = new QuizService(con);
        MafiaService mafiaService = new MafiaService(con);
        QuizController quizController = new QuizController(quizService, mafiaService);
        QuizOutputview quizOutputView = new QuizOutputview();

        this.quizInputView =  new QuizInputView(quizController, quizOutputView);
    }
}

