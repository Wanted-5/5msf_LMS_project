package com.lms.domain.category.dao;

import com.lms.domain.category.dto.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.lms.global.util.QueryUtil.getQuery;

public class CategoryDAO {
    private final Connection con;

    public CategoryDAO(Connection con) {
        this.con = con;
    }

    public List<CategoryDTO> findAllByVillageId(Long villageId) throws SQLException {
        String sql = getQuery("findAllByVillageId");
        List<CategoryDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setCategoryId(rs.getLong("category_id"));
                    dto.setCategoryName(rs.getString("category_name"));
                    dto.setVillageId(rs.getLong("village_id"));
                    dto.setCreatorId(rs.getLong("creator_id"));
                    dto.setCreatorNickname(rs.getString("creator_nickname"));
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    if (timestamp != null) {
                        dto.setCreatedAt(timestamp.toLocalDateTime());
                    }
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            System.out.println("[DAO Error] 카테고리 목록 조회 실패" + e.getMessage());
        }
        return list;
    }

    // category(강사카테고리 게시판  insert 메서드
    public int insert(CategoryDTO dto) throws SQLException {
        String sql = getQuery("category.insert");

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, dto.getCategoryName());
            pstmt.setLong(2, dto.getVillageId());
            pstmt.setLong(3, dto.getCreatorId());
            return pstmt.executeUpdate();
        }
    }

    public int update(CategoryDTO dto) throws SQLException {
        String sql = getQuery("category.update");

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, dto.getCategoryName());
            pstmt.setLong(2, dto.getCategoryId());
            pstmt.setLong(3, dto.getCreatorId());
            return pstmt.executeUpdate();
        }
    }

    public int delete(CategoryDTO dto) throws SQLException {
        String sql = getQuery("category.delete");

         try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, dto.getCategoryId());
            pstmt.setLong(2, dto.getCreatorId());
            return pstmt.executeUpdate();
        }
    }
}

