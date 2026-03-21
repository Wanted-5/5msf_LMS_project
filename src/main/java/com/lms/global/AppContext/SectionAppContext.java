package com.lms.global.AppContext;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.model.DAO.SectionDAO;
import com.lms.domain.section.model.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.section.view.SectionOutputView;

import java.sql.Connection;

public class SectionAppContext {

    public final SectionDAO sectionDAO;
    public final SectionService sectionService;
    public final SectionController sectionController;
    public final SectionOutputView sectionOutputView;
    public final SectionInputView sectionInputView;

    public SectionAppContext(Connection con) {
        SectionDAO sectionDAO = new SectionDAO();
        SectionService sectionService = new SectionService(con, sectionDAO);
        this.sectionDAO = new SectionDAO();
        this.sectionService = new SectionService(con, sectionDAO);
        this.sectionController = new SectionController(sectionService);
        this.sectionOutputView = new SectionOutputView();
        this.sectionInputView = new SectionInputView(sectionController, sectionOutputView);
    }
}