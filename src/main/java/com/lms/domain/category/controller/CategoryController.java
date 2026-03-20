package com.lms.domain.category.controller;

import com.lms.domain.category.dao.CategoryDAO;
import com.lms.domain.category.dto.CategoryDTO;
import com.lms.domain.category.service.CategoryService;

import java.util.List;
import java.util.Scanner;

public class CategoryController {
    private final CategoryService categoryService;
    private final Scanner sc = new Scanner(System.in);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void selectAll(Long villageId) {
        while (true) {
            System.out.println("\n===> 7. 게시판");
            System.out.println("\t- 1. 게시판 조회");
            System.out.println("\t- 2. 게시판 작성 하기");
            System.out.println("\t- 3. 내 글 관리");
            System.out.println("\t- 4. 메인페이지로 돌아가기");
            System.out.print("메뉴 선택 : ");

            String choice = sc.next();

            switch (choice) {
                case "1":
                    showCategorySelection(villageId);
                    break;
                case "2":
                    System.out.println("게시글 작성 기능을 아직 안만들었다이.");
                    break;
                case "3":
                    System.out.println("내 글 관리 기능 아직 구현안했따라이");
                    break;
                case "4":
                    System.out.println("---> 메인페이지로 이동");
                    return; // 메서드 종료 (메인 메뉴로 돌아감)
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }


    private void showCategorySelection(Long villageId) {
        System.out.println("\t=== 카테고리를 선택해주세요. ===");

        List<CategoryDTO> list = categoryService.getCategoryList(villageId);

        if (list.isEmpty()) {
            System.out.println("\t=== 생성된 카테고리가 없습니다. Q&A게시판에 문의해주세요. ===");
            System.out.println("\t---> 7. 게시판 콘솔창으로 이동 ");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t\t- " + (i + 1) + ". " + list.get(i).getCategoryName());
        }
        System.out.println("\t\t- " + (list.size() + 1) + ". 이전으로");

        System.out.println("카테고리 선택 : ");
        try {
            int categoryChoice = Integer.parseInt(sc.nextLine());

            if (categoryChoice == list.size() + 1) {
                System.out.println("\t\t---> 7. 게시판 콘솔창으로 이동");
                return;
            }

            if (categoryChoice > 0 && categoryChoice <= list.size()) {
                CategoryDTO selected = list.get(categoryChoice - 1);
                System.out.println("\t\t=== " + selected.getCategoryName() + " 목록 조회 (최신순) ===");

            } else {
                System.out.println("잘못된 번호입니다");
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
        }
    }
}