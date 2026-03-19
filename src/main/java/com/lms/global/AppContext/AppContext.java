package com.lms.global.AppContext;

import com.lms.domain.quiz.controller.QuizController;
import com.lms.domain.quiz.service.QuizService;
import com.lms.domain.quiz.view.QuizInputView;
import com.lms.domain.quiz.view.QuizOutputview;

import java.sql.Connection;

public class AppContext {

    public final QuizAppContext quizAppContext;

    public AppContext(Connection con) {
        this.quizAppContext = new QuizAppContext(con);
    }

    // 유저 커넥션
//    public void userAppContext(Connection con) {
//        UserService userService = new UserService(con);
//        UserController userController = new UserController(userService);
//        UserOutputView userOutputView = new UserOutputView();
//        UserInputView userInputView = new UserInputView(userController, userOutputView);
//    }

//    // 출결 커넥션
//    public void attendAppContext(Connection con) {
//        AttendService attendService = new AttendService(con);
//        AttendController attendController = new AttendController(attendService);
//        AttendOutputView attendOutputView = new AttendOutputView();
//        AttendInputView attendInputView = new AttendInputView(attendController, attendOutputView);
//    }
//
//    // 시티 커넥션
//    public void cityAppContext(Connection con) {
//        CityService cityService = new CityService(con);
//        CityController cityController = new CityController(cityService);
//        CityOutputView cityOutputView = new CityOutputView();
//        CityInputView cityInputView = new CityInputView(cityController, cityOutputView);
//    }
//
//    // 마을 커넥션
//    public void villageAppContext(Connection con) {
//        VillageService villageService = new VillageService(con);
//        VillageController villageController = new VillageController(villageService);
//        VillageOutputView villageOutputView = new VillageOutputView();
//        VillageInputView villageInputView = new VillageInputView(villageController, villageOutputView);
//    }
//
//    // 섹션 커넥션
//    public void sectionAppContext(Connection con) {
//        SectionService sectionService = new SectionService(con);
//        SectionController sectionController = new SectionController(sectionService);
//        SectionOutputView sectionOutputView = new SectionOutputView();
//        SectionInputView sectionInputView = new SectionInputView(sectionController, sectionOutputView);
//    }
//
//    // 카테고리 커넥션
//    public void categoryAppContext(Connection con) {
//        CategoryService categoryService = new CategoryService(con);
//        CategoryController categoryController = new CategoryController(categoryService);
//        CategoryOutputView categoryOutputView = new CategoryOutputView();
//        CategoryInputView categoryInputView = new CategoryInputView(categoryController, categoryOutputView);
//    }
//
//    // 게시판 커넥션
//    public void boardAppContext(Connection con) {
//        BoardService boardService = new BoardService(con);
//        BoardController boardController = new BoardController(boardService);
//        BoardOutputView boardOutputView = new BoardOutputView();
//        BoardInputView boardInputView = new BoardInputView(boardController, boardOutputView);
//    }
//
//    // 댓글 커넥션
//    public void commentAppContext(Connection con) {
//        CommentService commentService = new CommentService(con);
//        CommentController commentController = new CommentController(commentService);
//        CommentOutputView commentOutputView = new CommentOutputView();
//        CommentInputView commentInputView = new CommentInputView(commentController, commentOutputView);
//    }
//
//    // 마피아 커넥션
//    public void mafiaAppContext(Connection con) {
//        MafiaService mafiaService = new MafiaService(con);
//        MafiaController mafiaController = new MafiaController(mafiaService);
//        MafiaOutputView mafiaOutputView = new MafiaOutputView();
//        MafiaInputView mafiaInputView = new MafiaInputView(mafiaController, mafiaOutputView);
//
//    }

//    // 퀴즈 커넥션
//    public void quizAppContext(Connection con) {
//        QuizService quizService = new QuizService(con);
//        QuizController quizController = new QuizController(quizService);
//        QuizOutputview quizOutputView = new QuizOutputview();
//        QuizInputView quizInputView = new QuizInputView(quizController, quizOutputView);
//
//        quizInputView.displayMainMenu();
//    }

//    // 퀴즈 제출 커넥션
//    public void quizSubAppContext(Connection con) {
//        QuizSubService quizSubmissionService = new QuizSubService(con);
//        QuizSubController quizSubmissionController = new QuizSubController(quizSubmissionService);
//        QuizSubOutputView quizSubmissionOutputView = new QuizSubOutputView();
//        QuizSubInputView quizSubmissionInputView = new QuizSubInputView(quizSubmissionController, quizSubmissionOutputView);
//    }



}
