package com.lms.domain.users.service;

import com.lms.domain.users.dto.UserRole;
import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.request.SignupRequest;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.domain.users.dto.response.MyPageResponse;
import com.lms.domain.users.dto.response.MyPageUpdateResponse;
import com.lms.domain.users.dto.response.SignupResponse;
import com.lms.global.common.UserSession;
import com.lms.global.util.PasswordUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {

    private final UserDAO userDAO;
    private final Connection connection;

    public UserService(Connection connection) {
        this.userDAO = new UserDAO(connection);
        this.connection = connection;
    }

    public SignupResponse signup(SignupRequest request) {
        try {

            // 검증 객체 생성
            UserDTO existingUser = null;

            existingUser = userDAO.findByUsername(request.getUsername());

            // 존재 여부 검증
            if (existingUser != null) {
                throw new IllegalArgumentException("👪 이미 존재하는 회원입니다.");
            }

            // 비밀번호 암호화
            String hashedPassword = PasswordUtil.hash(request.getPassword());

            UserDTO newUser = new UserDTO(
                    null,
                    request.getUsername(),
                    hashedPassword,
                    request.getEmail(),
                    request.getName(),
                    request.getNickname(),
                    request.getPhoneNumber(),
                    request.getAddress(),
                    request.isGender(),
                    request.getIntroduction(),
                    null,
                    UserRole.STUDENT,
                    null,
                    null
            );

            Long newUserId = userDAO.insertUser(newUser);

            if (newUserId == null) {
                throw new RuntimeException("[🚨] 회원가입 처리에 실패했습니다. (DB 저장 실패)");
                // 추후 사용자 친화적인 mseeage로 변경
                // "시스템 오류로 로그인할 수 없습니다. 잠시 후 다시 시도해주세요."
            }

            newUser.setUserId(newUserId);

            return new SignupResponse(
                    newUser.getUsername(),
                    newUser.getName(),
                    newUser.getRole()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponse login(LoginRequest request) {
        UserDTO user = null;
        try {
            user = userDAO.findByUsername(request.getUsername());

            if (user == null) {
                throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
            }

            // 비밀번호 해싱작업
            String hashedInputPassword = PasswordUtil.hash(request.getPassword());

            if (!user.getPassword().equals(hashedInputPassword)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

            return new LoginResponse(
                    user.getUserId(),
                    user.getName(),
                    user.getNickname(),
                    user.getRole()
            );

        } catch (SQLException e) {
            throw new RuntimeException("[🚨] 시스템 오류로 로그인할 수 없습니다. 잠시 후 다시 시도해주세요.", e);
        }
    }

    public void logout() {
        if (!UserSession.isLoggedIn()) {
            throw new IllegalArgumentException("[❌] 현재 로그인 한 유저가 존재하지 않습니다.");
        }

        UserSession.logout();
    }

    public MyPageResponse findById() {

        try {
            Long currentUserId = UserSession.getLoggedInUser().getUserId();
            UserDTO user = userDAO.findById(currentUserId);

            if (user == null) {
                throw new IllegalArgumentException("[❌마이페이지 조회 실패❌] 존재하지 않는 유저입니다.");
            }

            return new MyPageResponse(
                    user.getName(),
                    user.getNickname(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getIntroduction()
            );

        } catch (SQLException e) {
            throw new RuntimeException("[🚨] 시스템 오류로 마이페이지를 조회할 수 없습니다. 잠시 후 다시 시도해주세요.", e);
        }
    }

    public MyPageUpdateResponse updateNickname(String newNickname) {

        LoginResponse currentUser = UserSession.getLoggedInUser();

        if (currentUser.getNickname().equals(newNickname)) {
            throw new IllegalArgumentException("기존과 동일한 닉네임입니다. 다른 명찰을 준비해 주세요.");
        }

        try {
            UserDTO updatedUser =userDAO.updateNickname(currentUser.getUserId(), newNickname);

            currentUser.setNickname(newNickname);

            return new MyPageUpdateResponse(
                    updatedUser.getNickname(),
                    "닉네임(명찰)이 성공적으로 변경되었습니다."
            );

        } catch (SQLException e) {
            throw new RuntimeException("명찰 교체 중 시스템 오류가 발생했습니다.", e);
        }

    }

    public MyPageUpdateResponse updateEmail(String newEmail) {
        LoginResponse currentUser = UserSession.getLoggedInUser();
        try {
            UserDTO existingUser = userDAO.findById(currentUser.getUserId());

            // 유저 정보 조회
            if (existingUser == null) {
                throw new IllegalStateException("유저 정보를 찾을 수 없습니다.");
            }

            if (existingUser.getEmail().equals(newEmail)) {
                throw new IllegalArgumentException("기존과 동일한 이메일입니다. 다른 이메일을 작성해 주세요.");
            }

            UserDTO updatedUser = userDAO.updateEmail(currentUser.getUserId(), newEmail);

            return new MyPageUpdateResponse(
                    updatedUser.getEmail(),
                    "이메일(연락처)이 성공적으로 변경되었습니다."
            );

        } catch (SQLException e) {
            throw new RuntimeException("이메일 변경 중 시스템 오류가 발생했습니다.", e);
        }
    }

    public void updatePassword(String currentPassword, String newPassword) {
        LoginResponse currentUser = UserSession.getLoggedInUser();
        try {
            UserDTO existingUser = userDAO.findById(currentUser.getUserId());

            if (existingUser == null) {
                throw new IllegalStateException("유저 정보를 찾을 수 없습니다.");
            }

            // 비밀번호 1차 검증
            if (!PasswordUtil.checkPassword(currentPassword, existingUser.getPassword())) {
                throw new IllegalArgumentException("현재 비밀번호와 일치하지 않습니다.");
            }

            // 이전과 같은 비번 검증
            if (PasswordUtil.checkPassword(newPassword, existingUser.getPassword())) {
                throw new IllegalArgumentException("동일한 비밀번호를 사용할 수 없습니다.");
            }

            String hashedNewPassword = PasswordUtil.hash(newPassword);

            userDAO.updatePassword(currentUser.getUserId(), hashedNewPassword);

            // 비밀번호 변경 후 로그아웃 진행
            UserSession.logout();

        } catch (SQLException e) {
            throw new RuntimeException("비밀번호 변경 중 시스템 오류가 발생했습니다.", e);
        }
    }

    public int updateRoleToInstructor(String username) {
        try {
            return userDAO.updateRoleToInstructor(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
