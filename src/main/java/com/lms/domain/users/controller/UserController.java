package com.lms.domain.users.controller;

import com.lms.domain.users.dao.UserDAO;
import com.lms.domain.users.service.UserService;

import java.sql.Connection;

public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }
}
