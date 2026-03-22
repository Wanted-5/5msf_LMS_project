package com.lms.domain.category.view;

import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.dto.CategoryDTO;

import java.util.Scanner;

public class CategoryInputView {
    private final CategoryController controller;
    private final CategoryOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    public CategoryInputView(CategoryController controller, CategoryOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void Categorystart() {
        while (true) {
            try {
                System.out.println("\n========== [관리자] 카테고리 관리 ==========");
                System.out.println("1. 목록 조회  2. 추가  3. 수정  4. 삭제  0. 이전으로");
                System.out.print("선택: ");

                String input = sc.nextLine();
                int menu = Integer.parseInt(input);

                if (menu == 0) break;

                switch (menu) {
                    case 1 -> showList();
                    case 2 -> addCategory();
                    case 3 -> updateCategory();
                    case 4 -> deleteCategory();
                    default -> System.out.println("🚨 잘못된 번호입니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" 숫자만 입력 가능합니다. (예: 1, 2, 3)");
            }
        }
    }


    // 1. 카테고리 목록 조회
    private void showList() {
        System.out.print("조회할 마을(Village) ID: ");
        try {
            Long vId = Long.parseLong(sc.nextLine());
            outputView.printCategoryList(controller.getCategoryList(vId));
        } catch (NumberFormatException e) {
            System.out.println(" 마을 ID는 숫자여야 합니다.");
        }
    }


    // 2. 카테고리 추가 (강사/관리자 전용)
    private void addCategory() {
        System.out.print("추가할 카테고리 이름: ");
        String name = sc.nextLine();

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(name);
        dto.setVillageId(1L);
        dto.setCreatorId(6L);


        // userRole STUDENT로 하면 학생 권한 , INSTRUCTOR로 하면 강사 권한
        if (controller.insert(dto, "STUDENT")) {
            outputView.printMessage("카테고리가 성공적으로 추가되었습니다.");
        } else {
            outputView.printMessage("추가 실패 (권한 부족 또는 입력 오류)");
        }
    }


    // 3. 카테고리 수정 (강사/관리자 전용)
    private void updateCategory() {
        try {
            System.out.print("수정할 카테고리 ID: ");
            Long id = Long.parseLong(sc.nextLine());
            System.out.print("새로운 카테고리 이름: ");
            String name = sc.nextLine();

            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(id);
            dto.setCategoryName(name);
            dto.setCreatorId(6L); // DAO에서 본인 확인용으로 사용

            if (controller.update(dto, "INSTRUCTOR")) {
                outputView.printMessage("✅ 카테고리가 수정되었습니다.");
            } else {
                outputView.printMessage("❌ 수정 실패 (권한 부족 또는 ID 불일치)");
            }
        } catch (NumberFormatException e) {
            System.out.println("🚨 카테고리 ID는 숫자여야 합니다.");
        }
    }

    // 4. 카테고리 삭제 (강사/관리자 전용)
    private void deleteCategory() {
        try {
            System.out.print("삭제할 카테고리 ID: ");
            Long id = Long.parseLong(sc.nextLine());

            //
            System.out.print("정말로 삭제하시겠습니까? (Y/N): ");
            if (!sc.nextLine().equalsIgnoreCase("Y")) {
                System.out.println("취소되었습니다.");
                return;
            }

            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(id);
            dto.setCreatorId(6L); // 작성자 본인 확인용


            if (controller.delete(dto, "INSTRUCTOR")) {
                outputView.printMessage("✅ 카테고리가 삭제되었습니다.");
            } else {
                outputView.printMessage("❌ 삭제 실패 (권한 부족 또는 카테고리에 게시글 존재)");
            }
        } catch (NumberFormatException e) {
            System.out.println(" 카테고리 ID는 숫자여야 합니다.");
        }
    }
}