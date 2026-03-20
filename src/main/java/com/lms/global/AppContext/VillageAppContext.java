package com.lms.global.common;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.village.view.VillageInputView;
import com.lms.domain.village.view.VillageOutputView;

import java.sql.Connection;

public class VillageAppContext {

    public final VillageInputView villageInputView;

    public VillageAppContext(Connection con) {
        SectionService sectionService = new SectionService(con);
        SectionController sectionController = new SectionController(sectionService);
        VillageOutputView villageOutputView = new VillageOutputView();
        SectionInputView sectionInputView = new SectionInputView(sectionController);
        this.villageInputView = new VillageInputView(villageOutputView, sectionInputView);
    }
}
