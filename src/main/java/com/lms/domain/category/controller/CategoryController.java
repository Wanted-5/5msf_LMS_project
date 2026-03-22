package com.lms.domain.category.controller;

import com.lms.domain.category.dto.CategoryDTO;
import com.lms.domain.category.service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 전체 조회
    public List<CategoryDTO> getCategoryList(Long villageId) {
        return categoryService.getCategoryList(villageId);
    }

    // 강사권한 카테고리 추가
    public boolean insert(CategoryDTO dto, String userRole) {
        return categoryService.insert(dto, userRole);
    }

    // 강사,관리자 권한 카테고리 수정
    public boolean update(CategoryDTO dto, String userRole) {
        return categoryService.update(dto, userRole);
    }

    // 강사, 관리자 권한 카테고리 삭제
    public boolean delete(CategoryDTO dto, String userRole) {
        return categoryService.delete(dto, userRole);
    }
}