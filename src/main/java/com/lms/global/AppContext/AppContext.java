package com.lms.global.AppContext;


import java.sql.Connection;

public class AppContext {

    public final UserAppContext userAppContext;
    public final CommentAppContext commentAppContext;

    public AppContext(Connection con) {
        this.userAppContext = new UserAppContext(con);
        this.commentAppContext = new CommentAppContext(con);

    }

}
