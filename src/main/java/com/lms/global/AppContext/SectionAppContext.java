package com.lms.global.AppContext;

import com.lms.domain.section.controller.SectionController;
import com.lms.domain.section.service.SectionService;
import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.section.view.SectionOutputView;

import java.sql.Connection;

public class SectionAppContext {

    public final SectionInputView sectionInputView;


    public SectionAppContext(Connection con) {
        SectionService sectionService = new SectionService(con);
        SectionController sectionController = new SectionController(sectionService);
        SectionOutputView sectionOutputView = new SectionOutputView();
        this.sectionInputView = new SectionInputView(sectionController, sectionOutputView);
    }
}