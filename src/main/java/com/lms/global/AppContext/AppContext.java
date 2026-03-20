package com.lms.global.AppContext;

import com.lms.global.common.VillageAppContext;
import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.service.UserService;
import com.lms.domain.users.view.UserInputView;
import com.lms.domain.users.view.UserOutputView;

import java.sql.Connection;

public class AppContext {

    public final UserAppContext userAppContext;
    public final CommentAppContext commentAppContext;
    //public final QuizAppContext quizAppContext;
    public final VillageAppContext villageAppContext;

    public AppContext(Connection con) {
        this.userAppContext = new UserAppContext(con);
        this.commentAppContext = new CommentAppContext(con);
        this.villageAppContext = new VillageAppContext(con);
        // this.quizAppContext = new QuizAppContext(con);
    }

}
