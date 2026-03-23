package com.lms.global.AppContext;

import com.lms.domain.learning.controller.LearningController;
import com.lms.domain.learning.service.LearningService;
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

        // Section에서 learging
        LearningService learningService = new LearningService(con);
        LearningController learningController = new LearningController(learningService);
        this.sectionInputView = new SectionInputView(sectionController, sectionOutputView, learningController);
    }
}