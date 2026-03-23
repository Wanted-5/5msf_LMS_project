package com.lms.global.AppContext;

import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.service.VillageService;
import com.lms.domain.village.view.AdminVillageInputView;
import com.lms.domain.village.view.InstructorVillageInputView;
import com.lms.domain.village.view.StudentVillageInputView;
import com.lms.domain.village.view.VillageOutputView;

import java.sql.Connection;

public class VillageAppContext {

    public final StudentVillageInputView studentVillageInputView;
    public final InstructorVillageInputView instructorVillageInputView;
    public final AdminVillageInputView adminVillageInputView;

    public VillageAppContext(Connection con) {
        VillageService villageService = new VillageService(con);
        VillageController villageController = new VillageController(villageService);
        VillageOutputView villageOutputView = new VillageOutputView();
        this.studentVillageInputView = new StudentVillageInputView(villageController, villageOutputView);
        this.instructorVillageInputView = new InstructorVillageInputView(villageController, villageOutputView);
        this.adminVillageInputView =  new AdminVillageInputView(villageController, villageOutputView);
    }
}
