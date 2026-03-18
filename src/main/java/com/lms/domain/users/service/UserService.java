package com.lms.domain.users.service;

import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.response.LoginResponse;
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

    public LoginResponse login(LoginRequest request) throws SQLException {
        UserDTO user = userDAO.findByUsername(request.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        // 비밀번호 해싱작업
        String hashedPassword = PasswordUtil.hash(request.getPassword());

        if (!user.getPassword().equals(hashedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getRole()
        );
    }
}
