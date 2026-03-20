package com.lms.domain.city.controller;

import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.city.dto.request.CreateCityRequest;
import com.lms.domain.city.dto.response.CreateCityResponse;
import com.lms.domain.city.service.CityService;

import java.util.List;

public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    public CreateCityResponse createCityProcess(CreateCityRequest request) throws Exception{

        return service.createCity(request);
    }

    public List<CityDTO> readCityProcess() throws Exception{

        List<CityDTO> cityDTOList = service.findAll();

        return cityDTOList;
    }


}
