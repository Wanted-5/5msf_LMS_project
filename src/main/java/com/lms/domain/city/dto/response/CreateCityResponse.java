package com.lms.domain.city.dto.response;

public class CreateCityResponse {
    private Long cityId;
    private String cityName;


    public CreateCityResponse(Long cityId, String cityName) {
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
