package com.lms.domain.users.service;

import com.lms.domain.users.dao.UserDAO;

import java.sql.Connection;

public class UserService {

    private final UserDAO userDAO;
    private final Connection connection;

    public UserService(Connection connection) {
        this.userDAO = new UserDAO(connection);
        this.connection = connection;
    }

}
