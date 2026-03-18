package com.lms.domain.users.view;

import com.lms.domain.users.controller.UserController;

import java.util.Scanner;

public class UserInputView {

    private final UserController userController;
    private final UserOutputView userOutputView;
    Scanner scanner = new Scanner(System.in);

    public UserInputView(UserController userController, UserOutputView userOutputView) {
        this.userController = userController;
        this.userOutputView = userOutputView;
    }
}
