package com.lms.global.AppContext;

import com.lms.domain.attendance.controller.AttendController;
import com.lms.domain.attendance.service.AttendService;
import com.lms.domain.attendance.view.AttendInputView;
import com.lms.domain.attendance.view.AttendOutputView;
import com.lms.domain.comment.controller.CommentController;
import com.lms.domain.comment.service.CommentService;
import com.lms.domain.comment.view.CommentOutputView;


import java.sql.Connection;

public class AttendAppContext {

    public final AttendInputView attendInputView;

    public AttendAppContext(Connection con) {
        AttendService attendService = new AttendService(con);
        AttendController attendController = new AttendController(attendService);
        AttendOutputView attendOutputView = new AttendOutputView();

        this.attendInputView = new AttendInputView(attendController, attendOutputView);
    }
}
