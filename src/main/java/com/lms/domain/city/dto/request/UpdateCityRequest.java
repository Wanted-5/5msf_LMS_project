package com.lms.domain.city.dto.request;

public class UpdateCityRequest {
    private Long cityId;
    private String cityName;
    private String description;

    public UpdateCityRequest(Long cityId, String cityName, String description) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.description = description;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }
}
