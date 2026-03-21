package com.lms.global.AppContext;

import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.service.UserService;
import com.lms.domain.users.view.UserInputView;
import com.lms.domain.users.view.UserOutputView;
import com.lms.global.common.VillageAppContext;

import java.sql.Connection;

public class AppContext {

    private static AppContext appContext;

    public final UserAppContext userAppContext;
    public final CityAppContext cityAppContext;
    public final CommentAppContext commentAppContext;
    public final QuizAppContext quizAppContext;
    public final MafiaAppContext mafiaAppContext;
    public final VillageAppContext villageAppContext;

    public AppContext(Connection con) {
        this.userAppContext = new UserAppContext(con);
        this.cityAppContext = new CityAppContext(con);
        this.commentAppContext = new CommentAppContext(con);
        this.mafiaAppContext = new MafiaAppContext(con);
        this.villageAppContext = new VillageAppContext(con);
        this.quizAppContext = new QuizAppContext(con);
    }

    // 최초에 한 번 세팅하는 메서드
    public static void init(Connection con) {
        if (appContext == null) {
            appContext = new AppContext(con);
        }
    }

    // get
    public static AppContext getAppContext() {
        return appContext;
    }

}
