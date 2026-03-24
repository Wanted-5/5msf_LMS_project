package com.lms.domain.users.controller;

import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.request.SignupRequest;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.MyPageResponse;
import com.lms.domain.users.dto.response.MyPageUpdateResponse;
import com.lms.domain.users.dto.response.SignupResponse;
import com.lms.domain.users.service.UserService;

public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인
    public LoginResponse loginProcess(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);

        return userService.login(request);
    }

    // 회원가입
    public SignupResponse signupProcess(SignupRequest request) throws Exception {

        return userService.signup(request);
    }

    // 로그아웃
    public void logout() {
        userService.logout();
    }

    // 마이페이지 목록 조회
    public MyPageResponse findById() throws Exception {
        return userService.findById();
    }

    // 비밀번호 변경
    public void updatePasswordProcess(String currentPassword, String newPassword) throws Exception{
        userService.updatePassword(currentPassword, newPassword);
    }

    // 이메일 변경
    public MyPageUpdateResponse updateEmailProcess(String newEmail) throws Exception{
        return userService.updateEmail(newEmail);
    }

    // 닉네임 변경
    public MyPageUpdateResponse updateNicknameProcess(String newNickname) throws Exception {
        return userService.updateNickname(newNickname);
    }

    // 권한 승격
    public int updateRoleToInstructor(String username) {
        return userService.updateRoleToInstructor(username);
    }
}
