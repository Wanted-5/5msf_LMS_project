package com.lms.domain.city.dao;

import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;

import java.sql.Connection;

public class CityDAO {

    private final Connection connection;

    public CityDAO(Connection connection) {
        this.connection = connection;
    }


    public int insertCity(String cityName, String description) {
        return 0;
    }
}
