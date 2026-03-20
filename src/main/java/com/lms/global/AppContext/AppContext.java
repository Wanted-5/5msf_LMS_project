package com.lms.global.AppContext;

<<<<<<< HEAD
import com.lms.global.common.VillageAppContext;
=======
import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.service.UserService;
import com.lms.domain.users.view.UserInputView;
import com.lms.domain.users.view.UserOutputView;
>>>>>>> 7cf81797c4116cfe5161e66038702007c50642c2

import java.sql.Connection;

public class AppContext {

<<<<<<< HEAD
    public final VillageAppContext villageAppContext;

    public AppContext(Connection con) {
        this.villageAppContext = new VillageAppContext(con);
    }
}
=======
    public final UserAppContext userAppContext;
//       public final CommentAppContext commentAppContext;
    //public final QuizAppContext quizAppContext;

    public AppContext(Connection con) {
        this.userAppContext = new UserAppContext(con);
//       this.commentAppContext = new CommentAppContext(con);
       // this.quizAppContext = new QuizAppContext(con);
    }

}
>>>>>>> 7cf81797c4116cfe5161e66038702007c50642c2
