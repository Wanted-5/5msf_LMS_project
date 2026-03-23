package com.lms.global.AppContext;

import com.lms.domain.city.controller.CityController;
import com.lms.domain.city.service.CityService;
import com.lms.domain.city.view.CityInputView;
import com.lms.domain.city.view.CityOutputView;
import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.enrollment.service.EnrollmentService;
import com.lms.domain.users.controller.UserController;
import com.lms.domain.users.service.UserService;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.service.VillageService;

import java.sql.Connection;

public class CityAppContext {

    public final CityInputView cityInputView;

    public CityAppContext(Connection con) {
        CityService cityService = new CityService(con);
        CityController cityController = new CityController(cityService);
        CityOutputView cityOutputView = new CityOutputView();

        UserService userService = new UserService(con);
        UserController userController = new UserController(userService);

        VillageService villageService = new VillageService(con);
        VillageController villageController = new VillageController(villageService);

        EnrollmentService enrollmentService = new EnrollmentService(con)
        EnrollmentController enrollmentController = new EnrollmentController(enrollmentService)
        this.cityInputView = new CityInputView(
                cityController,
                cityOutputView,
                userController,
                villageController,
                enrollmentController);
    }
}
