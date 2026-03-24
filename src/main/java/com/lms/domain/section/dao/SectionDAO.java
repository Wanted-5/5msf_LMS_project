package com.lms.domain.section.dao;

import com.lms.domain.section.dto.SectionDTO;
import com.lms.domain.section.dto.request.SectionDetailRequest;
import com.lms.domain.section.dto.response.CreateSectionRequest;
import com.lms.global.config.JDBCTemplate;
import com.lms.global.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    private final Connection connection;

    public SectionDAO(Connection connection) {
        this.connection = connection;
    }

    // 강의 전체 조회
    public List<SectionDTO> findSectionsByVillageId(long villageId) throws SQLException {
        List<SectionDTO> sectionDTOList = new ArrayList<>();

        String query = QueryUtil.getQuery("section.findSectionsByVillageId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sectionDTOList.add(convertToDTO(rs));
                }
            }
        }
        return sectionDTOList;
    }

    public SectionDTO findSectionBySectionId(SectionDetailRequest request) throws SQLException {

        String query = QueryUtil.getQuery("section.findSectionByVillageIdAndSectionId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, request.getVillageId());
            pstmt.setLong(2, request.getSectionId());

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    return convertToDTO(rs);
                }
            }
        }

        return null;
    }

    public SectionDTO findSectionById(Connection con, long sectionId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("section.findSectionById");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, sectionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return convertToDTO(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException("강의 상세 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return null;
    }

    public SectionDTO findSectionByVillageIdAndSectionId(Connection con, long villageId, long sectionId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("section.findSectionByVillageIdAndSectionId");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, sectionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return convertToDTO(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException("마을 내 강의 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return null;
    }

    public List<SectionDTO> findCompletedSectionsByIds(Connection con, List<Long> sectionIds) {
        List<SectionDTO> list = new ArrayList<>();

        if (sectionIds == null || sectionIds.isEmpty()) {
            return list;
        }

        StringBuilder sql = new StringBuilder("""
                SELECT section_id, village_id, chap_no, section_name, content, video_url, status
                FROM section
                WHERE status = 'ACTIVE'
                  AND section_id IN (
                """);

        for (int i = 0; i < sectionIds.size(); i++) {
            sql.append("?");
            if (i < sectionIds.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(") ORDER BY chap_no ASC");

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql.toString());

            for (int i = 0; i < sectionIds.size(); i++) {
                pstmt.setLong(i + 1, sectionIds.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                SectionDTO dto = new SectionDTO();
                dto.setSectionId(rs.getLong("section_id"));
                dto.setVillageId(rs.getLong("village_id"));
                dto.setChapNo(rs.getInt("chap_no"));
                dto.setSectionName(rs.getString("section_name"));
                dto.setContent(rs.getString("content"));
                dto.setVideoUrl(rs.getString("video_url"));
                dto.setStatus(rs.getString("status"));
                list.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("완료 강의 목록 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return list;
    }

    //comment, 강사 기능

    public Long insertSection(CreateSectionRequest request) throws SQLException {

        String sql = QueryUtil.getQuery("section.insertSection");

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, request.getVillageId());
            pstmt.setLong(2, request.getUserId());
            pstmt.setInt(3, request.getChapNo());
            pstmt.setString(4, request.getSectionName());
            pstmt.setString(5, request.getContent());
            pstmt.setString(6, request.getVideoUrl());

            int insertRows = pstmt.executeUpdate();

            if (insertRows == 0) {
                throw new SQLException("[DB error] 강의 등록에 실패했습니다.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("[DB error] 강의 등록은 성공, But ID를 가져오지 못했습니다.");
                }
            }
        }
    }

    // =================== 내부 편의 메서드 ===============
    private SectionDTO convertToDTO(ResultSet rs) throws SQLException {
        return new SectionDTO(
        rs.getLong("section_id"),
        rs.getLong("village_id"),
        rs.getLong("user_id"),
        rs.getInt("chap_no"),
        rs.getString("section_name"),
        rs.getString("content"),
        rs.getString("video_url"),
        rs.getString("status")
        );
    }
}