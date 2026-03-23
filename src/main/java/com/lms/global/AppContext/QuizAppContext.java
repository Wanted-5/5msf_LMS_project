package com.lms.global.AppContext;

import com.lms.domain.mafia.service.MafiaService;
import com.lms.domain.quiz.controller.QuizController;
import com.lms.domain.quiz.service.QuizService;
import com.lms.domain.quiz.view.QuizInputView;
import com.lms.domain.quiz.view.QuizOutputview;
import com.lms.domain.quizSubmission.controller.QuizSubController;
import com.lms.domain.quizSubmission.service.QuizSubService;
import com.lms.domain.quizSubmission.view.QuizSubInputView;
import com.lms.domain.quizSubmission.view.QuizSubOutputView;

import java.sql.Connection;

public class QuizAppContext {


    public final QuizInputView quizInputView;

    public QuizAppContext(Connection con) {

        QuizService quizService = new QuizService(con);
        MafiaService mafiaService = new MafiaService(con);
        QuizController quizController = new QuizController(quizService, mafiaService);
        QuizOutputview quizOutputView = new QuizOutputview();
        QuizSubService quizSubService = new QuizSubService(con);
        QuizSubController quizSubController = new QuizSubController(quizSubService);
        QuizSubOutputView quizSubOutputView = new QuizSubOutputView();
        QuizSubInputView quizSubInputView = new QuizSubInputView(quizSubController, quizSubOutputView);
        this.quizInputView = new QuizInputView(quizController, quizOutputView, quizSubInputView);
    }
}

