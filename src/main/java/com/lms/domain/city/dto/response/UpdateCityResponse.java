package com.lms.domain.city.dto.response;

import java.time.LocalDateTime;

public class UpdateCityResponse {
    private Long cityId;
    private String cityName;

    public UpdateCityResponse(Long cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }
}
