package com.lms.global.AppContext;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.service.VillageService;
import com.lms.domain.village.view.VillageInputView;
import com.lms.domain.village.view.VillageOutputView;

import java.sql.Connection;

public class VillageAppContext {

    public final VillageInputView villageInputView;
    public Object villageController;

    public VillageAppContext(Connection con) {
        VillageService villageService = new VillageService(con);
        VillageController villageController = new VillageController(villageService);
        VillageOutputView villageOutputView = new VillageOutputView();
        this.villageInputView = new VillageInputView(villageController, villageOutputView);
    }
}
