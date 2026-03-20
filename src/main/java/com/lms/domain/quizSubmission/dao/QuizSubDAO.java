package com.lms.domain.quizSubmission.dao;

import java.sql.Connection;

public class QuizSubDAO {

    private final Connection connection;

    public QuizSubDAO(Connection connection) {
        this.connection = connection;
    }
}
