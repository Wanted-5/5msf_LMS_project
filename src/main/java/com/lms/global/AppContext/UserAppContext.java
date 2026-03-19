package com.lms.global.AppContext;

import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.service.UserService;
import com.lms.domain.users.view.UserInputView;
import com.lms.domain.users.view.UserOutputView;

import java.sql.Connection;

public class UserAppContext {

    public final UserInputView userInputView;

    public UserAppContext(Connection con) {
        UserService userService = new UserService(con);
        UserController userController = new UserController(userService);
        UserOutputView userOutputView = new UserOutputView();

        this.userInputView = new UserInputView(userController, userOutputView);
    }
}
