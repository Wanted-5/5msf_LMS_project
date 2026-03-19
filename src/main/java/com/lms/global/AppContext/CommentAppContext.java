package com.lms.global.AppContext;

import com.lms.domain.comment.controller.CommentController;
import com.lms.domain.comment.service.CommentService;
import com.lms.domain.comment.view.CommentInputView;
import com.lms.domain.comment.view.CommentOutputView;

import java.sql.Connection;

public class CommentAppContext {

    public final CommentInputView commentInputView;


    public CommentAppContext(Connection con) {
        CommentService commentService = new CommentService(con);
        CommentController commentController = new CommentController(commentService);
        CommentOutputView commentOutputView = new CommentOutputView();

        this.commentInputView = new CommentInputView(commentController, commentOutputView);
        commentInputView.showInitialMenu();
    }

}
