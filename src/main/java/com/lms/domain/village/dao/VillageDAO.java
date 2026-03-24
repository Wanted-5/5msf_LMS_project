package com.lms.domain.village.dao;

import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.global.config.JDBCTemplate;
import com.lms.global.util.QueryUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Date.valueOf;
import static java.sql.Types.DATE;

public class VillageDAO {

    private final Connection connection;

    public VillageDAO(Connection connection) {
        this.connection = connection;
    }

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

    public Long createVillage(CreateVillageRequest request) throws SQLException {

        String query = QueryUtil.getQuery("village.insertVillage");

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, request.getCityId());
            pstmt.setString(2, request.getVillageName());
            pstmt.setString(3, request.getDescription());
            pstmt.setString(4, request.getInviteCode());
            if (request.getStartDate() != null) {
                // LocalDate를 JDBC가 이해할 수 있는 java.sql.Date로 변환해서 넣습니다.
                pstmt.setDate(5, valueOf(request.getStartDate()));
            } else {
                pstmt.setNull(5, DATE);
            }
            if (request.getEndDate() != null) {
                pstmt.setDate(6, valueOf(request.getEndDate()));
            } else {
                pstmt.setNull(6, DATE);
            }

            int insertRows = pstmt.executeUpdate();

            if (insertRows == 0) {
                throw new SQLException("마을 건설에 실패했습니다. [DB 문제]");
            }

            try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1); // city_id 리턴
                } else {
                    throw new SQLException("[error] 마을 건설에는 성공, but ID 획득 실패.");
                }
            }
        }
    }

    public List<VillageDTO> findAllVillages() throws SQLException {
        List<VillageDTO> villageDTOList = new ArrayList<>();

        String query = QueryUtil.getQuery("village.findAll");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                villageDTOList.add(convertToDTO(rs));
            }
        }
        return villageDTOList;
    }

    // TODO : 편의 메서드 구현하기
    // ================== 내부 편의 메서드 ===============
    private VillageDTO convertToDTO(ResultSet rs) {
        try {
            return new VillageDTO(
                    rs.getLong("village_id"),
                    rs.getLong("city_id"),
                    rs.getString("village_name"),
                    rs.getString("description"),
                    rs.getString("invite_code"),
                    rs.getObject("start_date", LocalDate.class),
                    rs.getObject("end_date", LocalDate.class),
                    rs.getString("status"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class)
            );
        } catch (SQLException e) {
            throw new RuntimeException("[error] VillageDTO 변환 작업 중 에러 발생", e);
        }
    }
}