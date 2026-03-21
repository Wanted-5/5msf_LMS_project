package com.lms.domain.attendance.service;

import com.lms.domain.attendance.dao.AttendDAO;

import java.sql.Connection;

public class AttendService {
    private final AttendDAO attendDAO;
    private final Connection connection;

    public AttendService(Connection connection) {
        this.attendDAO = new AttendDAO(connection);
        this.connection = connection;
    }


}
