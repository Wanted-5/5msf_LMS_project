package com.lms.domain.village.dao;

import com.lms.domain.village.dto.VillageDTO;
import com.lms.global.config.JDBCTemplate;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VillageDAO {

    public VillageDTO findVillageByInviteCode(Connection con, String inviteCode) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("findVillageByInviteCode");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, inviteCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                VillageDTO village = new VillageDTO();
                village.setVillageId(rs.getLong("village_id"));
                village.setVillageName(rs.getString("village_name"));

                return village;
            }
        } catch (Exception e) {
            throw new RuntimeException("Village 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return null;
    }

    public boolean isVillagePeriodValid(Connection con, long villageId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("isVillagePeriodValid");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, villageId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("Village 기간 검증 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return false;
    }

    public boolean isStudentApproved(Connection con, long villageId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = QueryUtil.getQuery("isStudentApproved");

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, villageId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("학생 승인 여부 조회 실패", e);
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return false;
    }
}