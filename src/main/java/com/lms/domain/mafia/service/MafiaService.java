package com.lms.domain.mafia.service;

import com.lms.domain.mafia.dao.MafiaDAO;

import java.sql.Connection;

public class MafiaService {

    private final MafiaDAO mafiaDAO;
    private final Connection connection;

    public MafiaService(Connection connection) {
        this.mafiaDAO = new MafiaDAO(connection);
        this.connection = connection;
    }
}
