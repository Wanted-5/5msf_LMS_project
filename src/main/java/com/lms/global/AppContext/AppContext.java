package com.lms.global.AppContext;
import com.lms.domain.users.controller.UserController;

import java.sql.Connection;

public class AppContext {

    private static AppContext appContext;

    public final UserAppContext userAppContext;
    public final CityAppContext cityAppContext;
    public final CommentAppContext commentAppContext;
    public final AttendAppContext attendAppContext;
    public final QuizAppContext quizAppContext;
    public final MafiaAppContext mafiaAppContext;
    public final VillageAppContext villageAppContext;
    public final EnrollmentAppContext enrollmentAppContext;
    public final BoardAppContext boardAppContext;
    public final CategoryAppContext categoryAppContext;
    public final SectionAppContext sectionAppContext;
     ;

    public AppContext(Connection con) {
        this.userAppContext = new UserAppContext(con);
        this.cityAppContext = new CityAppContext(con);
        this.commentAppContext = new CommentAppContext(con);
        this.attendAppContext = new AttendAppContext(con);
        this.mafiaAppContext = new MafiaAppContext(con);
        this.villageAppContext = new VillageAppContext(con);
        this.quizAppContext = new QuizAppContext(con);
        this.enrollmentAppContext = new EnrollmentAppContext(con);
        this.sectionAppContext = new SectionAppContext(con);
        this.categoryAppContext = new CategoryAppContext(con);
        this.boardAppContext = new BoardAppContext(con, categoryAppContext.categoryService,
                categoryAppContext.categoryController, categoryAppContext.categoryInputView, commentAppContext.commentInputView);
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
