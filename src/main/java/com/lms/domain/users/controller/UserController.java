package com.lms.domain.users.controller;

import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.request.SignupRequest;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.MyPageResponse;
import com.lms.domain.users.dto.response.MyPageUpdateResponse;
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

    // 마이페이지 목록 조회
    public MyPageResponse findById() throws Exception {
        return userService.findById();
    }

    public void updatePasswordProcess() {
    }

    public MyPageUpdateResponse updateEmailProcess(String newEmail) throws Exception{
        return userService.updateEmail(newEmail);
    }

    public MyPageUpdateResponse updateNicknameProcess(String newNickname) throws Exception {
        return userService.updateNickname(newNickname);
    }
}
