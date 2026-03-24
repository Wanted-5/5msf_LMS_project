package com.lms.global.AppContext;

import com.lms.domain.board.controller.BoardController;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.board.view.BoardInputView;
import com.lms.domain.board.view.BoardOutputView;
import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.service.CategoryService;
import com.lms.domain.category.view.CategoryInputView;
import com.lms.domain.comment.view.CommentInputView;

import java.sql.Connection;

public class BoardAppContext {

    public final BoardInputView boardInputView;

    public BoardAppContext(Connection con, CategoryService categoryService, CategoryController categoryController, CategoryInputView categoryInputView, CommentInputView commentInputView) {
        BoardService boardService = new BoardService(con);
        BoardController boardController = new BoardController(boardService);
        BoardOutputView boardOutputView = new BoardOutputView();


        this.boardInputView = new BoardInputView(boardController, boardOutputView, categoryService, categoryController,categoryInputView,commentInputView);
    }
}
