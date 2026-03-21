package com.lms.global.AppContext;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.DAO.SectionDAO;
import com.lms.domain.section.model.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.section.view.SectionOutputView;
import com.lms.domain.village.view.VillageInputView;
import com.lms.domain.village.view.VillageOutputView;

import java.sql.Connection;

public class VillageAppContext {

    public final VillageInputView villageInputView;

    public VillageAppContext(Connection con) {
        SectionDAO sectionDAO = new SectionDAO();
        SectionService sectionService = new SectionService(con, sectionDAO);
        SectionController sectionController = new SectionController(sectionService);
        VillageOutputView villageOutputView = new VillageOutputView();
        SectionOutputView sectionOutputView = new SectionOutputView();
        SectionInputView sectionInputView = new SectionInputView(sectionController, sectionOutputView);
        this.villageInputView = new VillageInputView(villageOutputView, sectionInputView);
    }
}
