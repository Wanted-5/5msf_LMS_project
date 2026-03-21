package com.lms.domain.enrollment.service;

import com.lms.domain.enrollment.dao.EnrollmentDAO;
import com.lms.domain.enrollment.dto.Response.VerifyInviteCodeResponse;
import com.lms.domain.village.dto.VillageDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class EnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final Connection connection;

    public EnrollmentService(Connection connection) {
        this.enrollmentDAO = new EnrollmentDAO(connection);
        this.connection = connection;
    }


    public VerifyInviteCodeResponse findVillageByInviteCode(String inviteCode) {

        if (inviteCode == null) {
            throw new IllegalArgumentException("빈 코드를 입력하셨습니다. 올바른 코드를 입력해주세요.");
        }

        try {
            return enrollmentDAO.findVillageByInviteCode(inviteCode);
        } catch (SQLException e) {
            throw new RuntimeException("존재하는 마을이 없습니다.", e);
        }
    }

    public void submitEnrollment(long currentUserId, long villageId) {
        try {
            enrollmentDAO.insertIntoEnrollment(currentUserId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
