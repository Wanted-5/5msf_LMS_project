package com.lms.domain.city.dto.response;

public class CreateCityResponse {
    private String cityId;
    private String cityName;
    private boolean isActive;

    public CreateCityResponse(String cityId, String cityName, boolean isActive) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.isActive = isActive;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isActive() {
        return isActive;
    }
}
