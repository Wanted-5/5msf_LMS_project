package com.lms.global.AppContext;

import com.lms.domain.enrollment.controller.EnrollmentController;
import com.lms.domain.enrollment.service.EnrollmentService;
import com.lms.domain.enrollment.view.EnrollmentInputView;
import com.lms.domain.enrollment.view.EnrollmentOutputView;
import com.lms.domain.enrollment.view.InstructorEnrollmentInputView;

import java.sql.Connection;

public class EnrollmentAppContext {

    public final EnrollmentService enrollmentService;
    public final EnrollmentController enrollmentController;
    public final EnrollmentOutputView enrollmentOutputView;
    public final EnrollmentInputView enrollmentInputView;
    public final InstructorEnrollmentInputView instructorEnrollmentInputView;

    public EnrollmentAppContext(Connection con) {
        this.enrollmentService = new EnrollmentService(con);
        this.enrollmentController = new EnrollmentController(enrollmentService);
        this.enrollmentOutputView = new EnrollmentOutputView();
        this.enrollmentInputView = new EnrollmentInputView(enrollmentController, enrollmentOutputView);
        this.instructorEnrollmentInputView = new InstructorEnrollmentInputView(enrollmentController, enrollmentOutputView);
    }
}