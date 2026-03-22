package com.lms.global.appContext;

import com.lms.domain.board.controller.BoardController;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.board.view.BoardInputView;
import com.lms.domain.board.view.BoardOutputView;
import com.lms.domain.category.service.CategoryService;

import java.sql.Connection;

public class BoardAppContext {

    public final BoardInputView boardInputView;

    public BoardAppContext(Connection con, CategoryService categoryService) {
        BoardService boardService = new BoardService(con);
        BoardController boardController = new BoardController(boardService);
        BoardOutputView boardOutputView = new BoardOutputView();


        this.boardInputView = new BoardInputView(boardController, boardOutputView, categoryService);
    }
}
