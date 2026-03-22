package com.lms.global.AppContext;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.village.controller.VillageController;
import com.lms.domain.village.service.VillageService;
import com.lms.domain.village.view.VillageInputView;
import com.lms.domain.village.view.VillageOutputView;

import java.sql.Connection;

public class VillageAppContext {

    public final VillageInputView villageInputView;

    // TODO : Section 파트 따로 분리하기
    public VillageAppContext(Connection con) {
        SectionService sectionService = new SectionService(con); // 추후 따로 빼기
        VillageService villageService = new VillageService(con);
        SectionController sectionController = new SectionController(sectionService); // 추후 따로 빼기
        VillageController villageController = new VillageController(villageService);
        VillageOutputView villageOutputView = new VillageOutputView();
        SectionInputView sectionInputView = new SectionInputView(sectionController); // 추후 따로 빼기
        this.villageInputView = new VillageInputView(villageOutputView, sectionInputView, villageController);
    }
}
