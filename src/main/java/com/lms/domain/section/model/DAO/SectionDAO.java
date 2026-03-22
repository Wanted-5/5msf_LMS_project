package com.lms.domain.section.model.DAO;

import com.lms.domain.section.model.DTO.SectionDTO;
import com.lms.global.config.JDBCTemplate;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    private final Connection con;

    public SectionDAO(Connection con) {
        this.con = con;
    }

    public List<SectionDTO> findSectionsByVillageId(Connection con, long villageId) {
        List<SectionDTO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("section.findSectionsByVillageId");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, villageId);
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
            throw new RuntimeException("마을별 강의 목록 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return list;
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
                SectionDTO dto = new SectionDTO();
                dto.setSectionId(rs.getLong("section_id"));
                dto.setVillageId(rs.getLong("village_id"));
                dto.setChapNo(rs.getInt("chap_no"));
                dto.setSectionName(rs.getString("section_name"));
                dto.setContent(rs.getString("content"));
                dto.setVideoUrl(rs.getString("video_url"));
                dto.setStatus(rs.getString("status"));
                return dto;
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
                SectionDTO dto = new SectionDTO();
                dto.setSectionId(rs.getLong("section_id"));
                dto.setVillageId(rs.getLong("village_id"));
                dto.setChapNo(rs.getInt("chap_no"));
                dto.setSectionName(rs.getString("section_name"));
                dto.setContent(rs.getString("content"));
                dto.setVideoUrl(rs.getString("video_url"));
                dto.setStatus(rs.getString("status"));
                return dto;
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
    public int insertSection(Connection con, Long villageId, int chapNo, String sectionName, String content, String videoUrl) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = QueryUtil.getQuery("section.insertSection");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, villageId);
            pstmt.setInt(2, chapNo);
            pstmt.setString(3, sectionName);
            pstmt.setString(4, content);
            pstmt.setString(5, videoUrl);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("섹션 INSERT 실패", e);
        } finally {
            JDBCTemplate.close(pstmt);
        }

        return result;
    }
}