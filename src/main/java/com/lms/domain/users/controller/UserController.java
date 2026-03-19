package com.lms.domain.users.controller;

import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.request.SignupRequest;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.SignupResponse;
import com.lms.domain.users.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    public LoginResponse loginProcess(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);

        return userService.login(request);
    }

    public SignupResponse signupProcess(SignupRequest request) throws Exception {

        return userService.signup(request);
    }

    public void logout() {
        userService.logout();
    }
}
