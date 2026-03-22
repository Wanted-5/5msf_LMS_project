package com.lms.global.appContext;

//import com.lms.domain.attendance.controller.AttendController;
//import com.lms.domain.attendance.service.AttendService;
//import com.lms.domain.attendance.view.AttendInputView;
//import com.lms.domain.attendance.view.AttendOutputView;
import com.lms.domain.board.controller.BoardController;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.board.view.BoardInputView;
import com.lms.domain.board.view.BoardOutputView;
import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.service.CategoryService;
import com.lms.domain.category.view.CategoryInputView;
import com.lms.domain.category.view.CategoryOutputView;
//import com.lms.domain.city.controller.CityController;
//import com.lms.domain.city.service.CityService;
//import com.lms.domain.city.view.CityInputView;
//import com.lms.domain.city.view.CityOutputView;
//import com.lms.domain.section.service.SectionService;
//import com.lms.domain.users.controller.UserController;
//import com.lms.domain.users.service.UserService;
//import com.lms.domain.users.view.UserInputView;
//import com.lms.domain.users.view.UserOutputView;

import java.sql.Connection;

public class AppContext {

    public final CategoryAppContext categoryAppContext;
    public final BoardAppContext boardAppContext;



    public AppContext(Connection con) {
        this.categoryAppContext = new CategoryAppContext(con);
        this.boardAppContext = new BoardAppContext(con,categoryAppContext.categoryService);

    }

}
