package com.lms.global.AppContext;

import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.enrollment.service.EnrollmentService;
import com.lms.domain.enrollment.view.EnrollmentInputView;
import com.lms.domain.enrollment.view.EnrollmentOutputView;

import java.sql.Connection;

public class EnrollmentAppContext {

    public final EnrollmentInputView enrollmentInputView;


    public EnrollmentAppContext(Connection con) {
        EnrollmentService enrollmentService = new EnrollmentService(con);
        EnrollmentController enrollmentController = new EnrollmentController(enrollmentService);
        EnrollmentOutputView enrollmentOutputView = new EnrollmentOutputView();
        this.enrollmentInputView = new EnrollmentInputView(enrollmentController, enrollmentOutputView);
    }
}
