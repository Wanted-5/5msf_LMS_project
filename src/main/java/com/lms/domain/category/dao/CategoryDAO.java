package com.lms.domain.category.dao;

import com.lms.domain.category.dto.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private final Connection con;

    public CategoryDAO(Connection con) {
        this.con = con;
    }

    public List<CategoryDTO> findAllByVillageId(Long villageId) throws SQLException {
        List<CategoryDTO> list = new ArrayList<>();


        String sql = "SELECT c.category_id, c.category_name, c.village_id, c.creator_id, " +
                "u.nickname AS creator_nickname, c.created_at " +
                "FROM category c " +
                "JOIN Users u ON c.creator_id = u.user_id " +
                "WHERE c.village_id = ? " +
                "ORDER BY c.category_id ASC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setLong(1, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    CategoryDTO cdto = new CategoryDTO();

                    cdto.setCategoryId(rs.getLong("category_id"));
                    cdto.setCategoryName(rs.getString("category_name"));
                    cdto.setVillageId(rs.getLong("village_id"));
                    cdto.setCreatorId(rs.getLong("creator_id"));

                    cdto.setCreatorNickname(rs.getString("creator_nickname"));

                    Timestamp timestamp = rs.getTimestamp("created_at");
                    if (timestamp != null) {
                        cdto.setCreatedAt(timestamp.toLocalDateTime());
                    }
                    list.add(cdto);
                }

            }
        } catch (SQLException e){
            System.out.println("[DAO Error] 카테고리 목록 조회 실패" + e.getMessage());
        }
            return list;
        }
    }


