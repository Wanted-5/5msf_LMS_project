package com.lms.global.appContext;

import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.service.CategoryService;
import com.lms.domain.category.view.CategoryInputView;
import com.lms.domain.category.view.CategoryOutputView;

import java.sql.Connection;

public class CategoryAppContext {

    public final CategoryInputView categoryInputView;

    public CategoryAppContext(Connection con){
        CategoryService categoryService = new CategoryService(con);
        CategoryController categoryController = new CategoryController(categoryService);
        CategoryOutputView categoryOutputView = new CategoryOutputView();

        this.categoryInputView = new CategoryInputView(categoryController, categoryOutputView);
    }

}
