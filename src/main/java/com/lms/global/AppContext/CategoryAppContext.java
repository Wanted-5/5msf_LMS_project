package com.lms.global.AppContext;

import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.service.CategoryService;
import com.lms.domain.category.view.CategoryInputView;
import com.lms.domain.category.view.CategoryOutputView;

import java.sql.Connection;

public class CategoryAppContext {
    public final CategoryService categoryService;
    public final CategoryController categoryController;
    public final CategoryOutputView categoryOutputView;
    public final CategoryInputView categoryInputView;


    public CategoryAppContext(Connection con){
        this.categoryService = new CategoryService(con);
        this.categoryController = new CategoryController(this.categoryService);
        this.categoryOutputView = new CategoryOutputView();

        this.categoryInputView = new CategoryInputView(this.categoryController, this.categoryOutputView, this.categoryService);
    }

}
