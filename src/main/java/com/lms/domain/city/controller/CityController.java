package com.lms.domain.city.controller;

import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.city.service.CityService;

public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    public CreateCityResponse createCityProcess(String cityName, String description) throws Exception{

        CreateCityRequest request = new CreateCityRequest(cityName, description);

        return service.createCity(request);
    }
}
