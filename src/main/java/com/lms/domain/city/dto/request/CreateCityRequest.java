package com.lms.domain.city.dto.request;

public class CreateCityRequest {
    private String cityName;
    private String description;

    public CreateCityRequest(String cityName, String description) {
        this.cityName = cityName;
        this.description = description;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }
}
