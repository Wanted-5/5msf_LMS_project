package com.lms.domain.users.dao;

import com.lms.domain.users.constant.UserRole;
import com.lms.domain.users.dto.UserDTO;
import com.lms.global.util.QueryUtil;

import java.sql.*;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    //
    public UserDTO findById(Long userId) throws SQLException {

        String query = QueryUtil.getQuery("users.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return convertToDTO(rs);
                }
            }
        }
        return null;
    }

    public Long insertUser(UserDTO newUser) throws SQLException {

        String query = QueryUtil.getQuery("users.insertUser");

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getName());
            pstmt.setString(5, newUser.getNickname());
            pstmt.setString(6, newUser.getPhoneNumber());
            pstmt.setString(7, newUser.getAddress());
            pstmt.setBoolean(8, newUser.isGender());
            pstmt.setString(9, newUser.getIntroduction());
            pstmt.setString(10, newUser.getRole().name());

            int resultQueryRow = pstmt.executeUpdate();

            if (resultQueryRow > 0) {
                // ResultSet도 끝나면 닫히도록 try문으로 감싸기
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }

        return null;
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

    public UserDTO updateNickname(Long userId, String newNickname) throws SQLException {

        String query = QueryUtil.getQuery("users.updateNickname");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newNickname);
            pstmt.setLong(2, userId);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                throw new SQLException("업데이트된 유저가 없습니다. (존재하지 않는 User ID)");
            }
        }
        return findById(userId);
    }

    public UserDTO updateEmail(Long userId, String newEmail) throws SQLException {

        String query = QueryUtil.getQuery("users.updateEmail");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newEmail);
            pstmt.setLong(2, userId);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                throw new SQLException("업데이트된 유저가 없습니다. (존재하지 않는 User ID)");
            }
        }
        return findById(userId);
    }

    public void updatePassword(Long userId, String hashedNewPassword) throws SQLException {

        String query = QueryUtil.getQuery("users.updatePassword");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, hashedNewPassword);
            pstmt.setLong(2, userId);

            int rs = pstmt.executeUpdate();

            if (rs == 0) {
                throw new SQLException("업데이트된 유저가 없습니다. (존재하지 않는 User ID)");
            }
        }
    }


    // ---------- 내부 편의 메서드 ----------------
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
