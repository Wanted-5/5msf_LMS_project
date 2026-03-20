package com.lms.domain.quizSubmission.service;

import com.lms.domain.quizSubmission.dao.QuizSubDAO;

import java.sql.Connection;

public class QuizSubService {

    private final QuizSubDAO quizsubDAO;
    private final Connection connection;


    public QuizSubService(Connection connection) {
        this.quizsubDAO = new QuizSubDAO(connection);
        this.connection = connection;
    }
}
