package com.lms.domain.category.view;

import com.lms.domain.category.dto.CategoryDTO;

import java.util.List;


public class CategoryOutputView {

        public void printCategoryList(List<CategoryDTO> list) {
            System.out.println("\n===== 카테고리 목록 =====");
            if (list.isEmpty()) {
                System.out.println("등록된 카테고리가 없습니다.");
            } else {
                for (CategoryDTO dto : list) {
                    System.out.printf("[ID: %d] 이름: %s\n", dto.getCategoryId(), dto.getCategoryName());
                }
            }
            System.out.println("========================");
        }

        public void printMessage(String message) {
            System.out.println(">> " + message);
        }

}
