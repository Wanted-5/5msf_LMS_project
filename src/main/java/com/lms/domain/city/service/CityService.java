package com.lms.domain.city.service;


import com.lms.domain.city.dao.CityDAO;
import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.users.dto.UserDTO;
import com.lms.global.common.UserSession;

import java.sql.Connection;

public class CityService {

    private final CityDAO cityDAO;
    private final Connection connection;

    public CityService(Connection connection) {
        this.cityDAO = new CityDAO(connection);
        this.connection = connection;
    }

    public CreateCityResponse createCity(CreateCityRequest request) {

        //TODO : 동일한 이름인지 검증하는 로직도 추가해야됨.

        if (request.getCityName() == null || request.getCityName().trim().isEmpty()) {
            throw new IllegalArgumentException("도시 이름은 필수입니다. 공허한 도시는 건설할 수 없습니다.");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("도시 설명은 필수입니다. 도시 설명을 입력해주세요.");
        }

        if (request.getDescription().length() > 200) {
            throw new IllegalArgumentException("도시 설명이 너무 깁니다. 200자 이내로 작성해 주세요.");
        }

        //TODO : 오늘 저녁 여기부터 구현하기
        int result = cityDAO.insertCity(request.getCityName(), request.getDescription());

        return null;
    }
}
