package com.lms.domain.mafia.dao;

import java.sql.Connection;

public class MafiaDAO {

    private final Connection connection;

    public MafiaDAO(Connection connection) {
        this.connection = connection;
    }
}
