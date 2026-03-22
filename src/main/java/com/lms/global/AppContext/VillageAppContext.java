package com.lms.global.AppContext;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.DAO.SectionDAO;
import com.lms.domain.section.model.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.section.view.SectionOutputView;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.service.VillageService;
import com.lms.domain.village.view.VillageInputView;
import com.lms.domain.village.view.VillageOutputView;

import java.sql.Connection;

public class VillageAppContext {

//    public final VillageInputView villageInputView;

    // TODO : Section 파트 따로 분리하기
    public VillageAppContext(Connection con) {
//        VillageService villageService = new VillageService(con);
//        VillageController villageController = new VillageController(villageService);
//        VillageOutputView villageOutputView = new VillageOutputView();
//        this.villageInputView = new VillageInputView(villageController,villageOutputView);
    }
}
