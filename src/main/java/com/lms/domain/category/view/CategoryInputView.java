package com.lms.domain.category.view;

import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.dto.CategoryDTO;
import com.lms.domain.category.service.CategoryService;
import com.lms.global.common.UserSession;

import java.util.List;
import java.util.Scanner;

public class CategoryInputView {
    private final CategoryController controller;
    private final CategoryOutputView outputView;
    private final CategoryService categoryService;
    private final Scanner sc = new Scanner(System.in);

    public CategoryInputView(CategoryController controller, CategoryOutputView outputView, CategoryService categoryService) {
        this.controller = controller;
        this.outputView = outputView;
        this.categoryService = categoryService;
    }

    public void Categorystart(Long villageId) {
        while (true) {
            try {
                System.out.println("\n========== [관리자] 카테고리 관리 ==========");
                System.out.println("1. 목록 조회  2. 추가  3. 수정  4. 삭제  0. 이전으로");
                System.out.print("선택: ");

                String input = sc.nextLine();
                int menu = Integer.parseInt(input);

                if (menu == 0) break;

                switch (menu) {
                    case 1 -> showList(villageId);
                    case 2 -> createCategory(villageId);
                    case 3 -> updateCategory(villageId);
                    case 4 -> deleteCategory(villageId);
                    default -> System.out.println("🚨 잘못된 번호입니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" 숫자만 입력 가능합니다. (예: 1, 2, 3)");
            }
        }
    }


    // 1. 카테고리 목록 조회
    private void showList(Long villageId) {

        try {
            outputView.printCategoryList(controller.getCategoryList(villageId));
        } catch (NumberFormatException e) {
            System.out.println("조회 할 내용이 없습니다.");
        }
    }


    private void createCategory(Long villageId) {
        System.out.println("=== 신규 게시판 카테고리를 생성합니다. ===");
        System.out.print("카테고리 이름 입력 : ");
        String name = sc.nextLine();

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(name);
        dto.setVillageId(villageId);
        dto.setCreatorId(UserSession.getLoggedInUser().getUserId());

        boolean success = controller.insert(dto,
                UserSession.getLoggedInUser().getRole().name());
        if (success) {
            System.out.println("=== " + name + " 카테고리가 생성되었습니다. ===");
        } else {
            System.out.println("카테고리 생성에 실패했습니다.");
        }
    }


    // 3. 카테고리 수정 (강사/관리자 전용)
    private void updateCategory(Long villageId) {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(villageId);
        if (categoryList.isEmpty()) {
            System.out.println("수정할 카테고리가 없습니다.");
            return;
        }

        System.out.println("=================================");
        System.out.println("         카테고리 목록");
        System.out.println("=================================");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i).getCategoryName());
        }
        System.out.print("수정할 카테고리 번호 입력 : ");


        int choice = inputInt();
        if (choice < 1 || choice > categoryList.size()) {
            System.out.println("유효하지 않은 번호입니다.");
            return;
        }

        System.out.print("새로운 카테고리 이름 입력 : ");
        String newName = sc.nextLine();

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(categoryList.get(choice - 1).getCategoryId());
        dto.setCategoryName(newName);
        dto.setCreatorId(UserSession.getLoggedInUser().getUserId());

        boolean success = controller.update(dto,
                UserSession.getLoggedInUser().getRole().name());
        if (success) {
            System.out.println("=== 카테고리가 수정되었습니다. ===");
        } else {
            System.out.println("카테고리 수정에 실패했습니다.");
        }
    }

    // 4. 카테고리 삭제 (강사/관리자 전용)
    private void deleteCategory(Long villageId) {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(villageId);
        if (categoryList.isEmpty()) {
            System.out.println("삭제할 카테고리가 없습니다.");
            return;
        }

        System.out.println("=================================");
        System.out.println("         카테고리 목록");
        System.out.println("=================================");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i).getCategoryName());
        }
        System.out.print("삭제할 카테고리 번호 입력 : ");

        int choice = inputInt();
        if (choice < 1 || choice > categoryList.size()) {
            System.out.println("유효하지 않은 번호입니다.");
            return;
        }

        System.out.print("정말 삭제하시겠습니까? (Y/N) : ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("취소되었습니다.");
            return;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(categoryList.get(choice - 1).getCategoryId());
        dto.setCreatorId(UserSession.getLoggedInUser().getUserId());

        boolean success = controller.delete(dto,
                UserSession.getLoggedInUser().getRole().name());
        if (success) {
            System.out.println("=== 카테고리가 삭제되었습니다. ===");
        } else {
            System.out.println("카테고리 삭제에 실패했습니다.");
        }
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}