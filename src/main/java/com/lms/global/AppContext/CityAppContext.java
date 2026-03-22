package com.lms.global.AppContext;

import com.lms.domain.city.controller.CityController;
import com.lms.domain.city.service.CityService;
import com.lms.domain.city.view.CityInputView;
import com.lms.domain.city.view.CityOutputView;

import java.sql.Connection;

public class CityAppContext {

    public final CityInputView cityInputView;

    public CityAppContext(Connection con) {
        CityService cityService = new CityService(con);
        CityController cityController = new CityController(cityService);
        CityOutputView cityOutputView = new CityOutputView();
        this.cityInputView = new CityInputView(cityController, cityOutputView);
    }
}
