package com.lms.domain.city.dao;

import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.global.util.QueryUtil;

import java.sql.*;

public class CityDAO {

    private final Connection connection;

    public CityDAO(Connection connection) {
        this.connection = connection;
    }


    public Long insertCity(String cityName, String description) throws SQLException {

        String query = QueryUtil.getQuery("city.insertCity");

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cityName);
            pstmt.setString(2, description);
            pstmt.setBoolean(3, true);

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
}
