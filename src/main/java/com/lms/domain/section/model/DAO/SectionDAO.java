package com.lms.domain.section.model.DAO;

import com.lms.domain.section.model.DTO.SectionDTO;
import com.lms.global.config.JDBCTemplate;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    public List<SectionDTO> findSectionsByVillageId(Connection con, long villageId) {
        List<SectionDTO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("findSectionsByVillageId");

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

        String sql = QueryUtil.getQuery("findSectionById");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, sectionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                SectionDTO section = new SectionDTO();
                section.setSectionId(rs.getLong("section_id"));
                section.setVillageId(rs.getLong("village_id"));
                section.setChapNo(rs.getInt("chap_no"));
                section.setSectionName(rs.getString("section_name"));
                section.setContent(rs.getString("content"));
                section.setVideoUrl(rs.getString("video_url"));
                section.setStatus(rs.getString("status"));
                return section;
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

        String sql = QueryUtil.getQuery("findSectionByVillageIdAndSectionId");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, sectionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                SectionDTO section = new SectionDTO();
                section.setSectionId(rs.getLong("section_id"));
                section.setVillageId(rs.getLong("village_id"));
                section.setChapNo(rs.getInt("chap_no"));
                section.setSectionName(rs.getString("section_name"));
                section.setContent(rs.getString("content"));
                section.setVideoUrl(rs.getString("video_url"));
                section.setStatus(rs.getString("status"));
                return section;
            }
        } catch (Exception e) {
            throw new RuntimeException("마을 내 강의 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return null;
    }
}