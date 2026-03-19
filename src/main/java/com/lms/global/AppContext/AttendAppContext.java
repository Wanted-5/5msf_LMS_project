//package com.lms.global.AppContext;
//
//import com.lms.domain.comment.controller.CommentController;
//import com.lms.domain.comment.service.CommentService;
//import com.lms.domain.comment.view.CommentInputView;
//import com.lms.domain.comment.view.CommentOutputView;
//
//import java.sql.Connection;
//
//public class AttendAppContext {
//
//    public final AttendInputView attendInputView;
//
//    public AttendAppContext(Connection con) {
//        CommentService commentService = new CommentService(con);
//        CommentController commentController = new CommentController(commentService);
//        CommentOutputView commentOutputView = new CommentOutputView();
//
//        this.attendInputView = new CommentInputView(commentController, commentOutputView);
//        attendInputView.메서드명();
//
//    }
//}
