package com.lms.domain.city.dto.request;

public class CreateCityRequest {
    private Long creatorId;
    private String cityName;
    private String description;

    public CreateCityRequest(Long creatorId, String cityName, String description) {
        this.creatorId = creatorId;
        this.cityName = cityName;
        this.description = description;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }
}
