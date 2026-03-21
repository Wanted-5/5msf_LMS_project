package com.lms.domain.city.dao;

import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.UpdateCityRequest;
import com.lms.global.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    private final Connection connection;

    public CityDAO(Connection connection) {
        this.connection = connection;
    }


    public CityDTO findById(Long cityId) throws SQLException {

        String query = QueryUtil.getQuery("city.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, cityId);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                return convertToDTO(rset);
            }
        }
        return null;
    }


    public Long insertCity(Long creatorId, String cityName, String description) throws SQLException {

        String query = QueryUtil.getQuery("city.insertCity");

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cityName);
            pstmt.setString(2, description);
            pstmt.setLong(3, creatorId);
            pstmt.setBoolean(4, true);

            int insertRows = pstmt.executeUpdate();

            if (insertRows == 0) {
                throw new SQLException("도시 건설에 실패했습니다. [DB 문제]");
            }

            try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1); // city_id 리턴
                } else {
                    throw new SQLException("[error] 도시 건설에는 성공, but ID 획득 실패.");
                }
            }
        }
    }

    public List<CityDTO> findAll() throws SQLException {
        List<CityDTO> cityDTOList = new ArrayList<>();

        String query = QueryUtil.getQuery("city.findAll");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset =pstmt.executeQuery();

            while (rset.next()) {
                cityDTOList.add(convertToDTO(rset));
            }
        }
        return cityDTOList;
    }

    public CityDTO updateCity(UpdateCityRequest request) throws SQLException {
        String query = QueryUtil.getQuery("city.updateCity");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, request.getCityName());
            pstmt.setString(2, request.getDescription());
            pstmt.setLong(3, request.getCityId());

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("[❌] 도시 수정에 실패했습니다. (존재하지 않는 행정 코드)");
            }

            return findById(request.getCityId());
        }
    }

    public void updateStatus(boolean newStatus, long cityId) throws SQLException {
        String query = QueryUtil.getQuery("city.updateStatus");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setBoolean(1, newStatus);
            pstmt.setLong(2, cityId);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("[❌] 도시 비활성화에 실패했습니다. (존재하지 않는 행정 코드)");
            }
        }
    }

    // ============= 내부 편의 메서드 =======================

    private CityDTO convertToDTO (ResultSet rset) {
        try {
            return new CityDTO(
                    rset.getLong("city_id"),
                    rset.getLong("creator_id"),
                    rset.getString("city_name"),
                    rset.getString("description"),
                    rset.getBoolean("status"),
                    rset.getTimestamp("created_at").toLocalDateTime(),
                    rset.getTimestamp("updated_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("[error] DTO 변환 작업 중 에러 발생", e);
        }
    }
}
