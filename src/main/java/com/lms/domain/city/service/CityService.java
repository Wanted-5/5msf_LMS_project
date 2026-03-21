package com.lms.domain.city.service;


import com.lms.domain.city.dao.CityDAO;
import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.domain.city.dto.request.UpdateCityRequest;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.city.dto.response.UpdateCityResponse;
import com.lms.global.common.UserSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        try {
            Long newCityId = cityDAO.insertCity(
                    request.getCreatorId(),
                    request.getCityName(),
                    request.getDescription());

            return new CreateCityResponse(
                    newCityId,
                    request.getCityName()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CityDTO> findAll() {

        try {
            return cityDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("[error]데이터 베이스 조회 실패", e);
        }
    }

    public UpdateCityResponse updateCity(UpdateCityRequest request) {

        if (request.getCityName() == null || request.getCityName().trim().isEmpty()) {
            throw new IllegalArgumentException("도시 이름은 필수입니다. 공허한 도시는 건설할 수 없습니다.");
        }

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("도시 설명은 필수입니다. 도시 설명을 입력해주세요.");
        }

        try {
            CityDTO existingCity = cityDAO.findById(request.getCityId());

            if (request.getCityName().equals(existingCity.getCityName())) {
                throw new IllegalArgumentException("이전과 동일한 이름으로 도시를 건설 할 수 없습니다.");
            }

            CityDTO updatedCity = cityDAO.updateCity(request);

            return new UpdateCityResponse(
                    updatedCity.getCityId(),
                    updatedCity.getCityName()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean deactivateCity(long cityId) {
        try {
            CityDTO existingCity = cityDAO.findById(cityId);

            if (existingCity == null) {
                throw new IllegalStateException("[error] 행정코드: " + cityId + "번 도시는 존재하지 않습니다.");
            }

            boolean newStatus = !existingCity.getIsActive();

            cityDAO.updateStatus(newStatus, cityId);

            return newStatus;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
