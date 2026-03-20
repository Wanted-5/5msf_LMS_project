package com.lms.global.AppContext;

import com.lms.global.common.VillageAppContext;

import java.sql.Connection;

public class AppContext {
    public final VillageAppContext villageAppContext;

//    public final UserAppContext userAppContext;

    public AppContext(Connection con) {
        this.villageAppContext = new VillageAppContext(con);
//        this.userAppContext = new UserAppContext(con);
//       this.commentAppContext = new CommentAppContext(con);
        // this.quizAppContext = new QuizAppContext(con);
    }

}
