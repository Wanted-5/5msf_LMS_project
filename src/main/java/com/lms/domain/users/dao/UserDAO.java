package com.lms.domain.users.dao;

import com.lms.domain.users.constant.UserRole;
import com.lms.domain.users.dto.UserDTO;
import com.lms.domain.users.dto.request.LoginRequest;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


    public UserDTO findByUsername(String username) throws SQLException {

        String query = QueryUtil.getQuery("users.findByUsername");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return convertToDTO(rs);
                }
            }
        }
        return null;
    }


    // ---------- 내부 메서드 ----------------
    private UserDTO convertToDTO(ResultSet rs) throws SQLException {
        return new UserDTO(
                rs.getLong("user_id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("nickname"),
                rs.getString("phone_number"),
                rs.getString("address"),
                rs.getBoolean("gender"),
                rs.getString("introduction"),
                rs.getString("profile_img_url"),
                UserRole.valueOf(rs.getString("role")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
