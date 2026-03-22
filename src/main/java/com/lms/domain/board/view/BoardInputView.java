package com.lms.domain.board.view;

import com.lms.domain.board.controller.BoardController;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.dto.CategoryDTO;
import com.lms.domain.category.service.CategoryService;
import com.lms.domain.users.dto.UserRole;
import com.lms.global.common.UserSession;

import java.util.List;
import java.util.Scanner;

public class BoardInputView {

    private final BoardController boardController;
    private final BoardOutputView boardOutputView;
    private final CategoryService categoryService;
    private final CategoryController categoryController;
    private final Scanner sc = new Scanner(System.in);

    public BoardInputView(BoardController boardController, BoardOutputView boardOutputView,
                          CategoryService categoryService, CategoryController categoryController) {
        this.boardController = boardController;
        this.boardOutputView = boardOutputView;
        this.categoryService = categoryService;
        this.categoryController = categoryController;
    }

    public void boardFirstMenu() {
        while (true) {
            UserRole role = UserSession.getLoggedInUser().getRole();

            System.out.println();
            System.out.println("=================================");
            System.out.println("            게시판 메뉴             ");
            System.out.println("=================================");
            System.out.println("1. 게시판 조회");
            System.out.println("2. 게시판 작성");
            System.out.println("3. 내 글 관리");

            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                System.out.println("4. 카테고리 생성");
                System.out.println("5. 카테고리 수정");
                System.out.println("6. 카테고리 삭제");
                System.out.println("7. 메인페이지로 돌아가기");
            } else {
                System.out.println("4. 메인페이지로 돌아가기");
            }
            System.out.print("번호를 입력해주세요 : ");

            int input = inputInt();

            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                switch (input) {
                    case 1: searchBoard(); break;
                    case 2: writeBoard(); break;
                    case 3: myBoardMenu(); break;
                    case 4: createCategory(); break;
                    case 5: updateCategory(); break;
                    case 6: deleteCategory(); break;
                    case 7: return;
                    default: System.out.println("1~7까지 다시 선택해주세요.");
                }
            } else {
                switch (input) {
                    case 1: searchBoard(); break;
                    case 2: writeBoard(); break;
                    case 3: myBoardMenu(); break;
                    case 4: return;
                    default: System.out.println("1~4까지 다시 선택해주세요.");
                }
            }
        }
    }

    private void searchBoard() {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(1L);

        if (categoryList.isEmpty()) {
            System.out.println("=== 생성된 카테고리가 없습니다. 관리자에게 문의해주세요. ===");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         카테고리 선택");
            System.out.println("=================================");
            for (int i = 0; i < categoryList.size(); i++) {
                System.out.println((i + 1) + ". " + categoryList.get(i).getCategoryName());
            }
            System.out.println((categoryList.size() + 1) + ". 이전으로");
            System.out.print("번호를 입력해주세요 : ");

            int choice = inputInt();
            if (choice == categoryList.size() + 1) return;

            if (choice < 1 || choice > categoryList.size()) {
                System.out.println("유효하지 않은 번호입니다.");
                continue;
            }

            Long categoryId = categoryList.get(choice - 1).getCategoryId();
            List<BoardDTO> boardList = boardController.findAllBoard(categoryId);
            boardOutputView.printBoardList(boardList);

            if (boardList.isEmpty()) continue;

            System.out.print("상세 조회할 게시글 번호를 입력해주세요 (0: 이전으로) : ");
            long boardId = inputLong();
            if (boardId == 0) return;

            BoardDTO board = boardController.findById(boardId);
            if (board == null) {
                System.out.println("존재하지 않는 게시글입니다.");
                return;
            }

            boardOutputView.printBoardDetail(board);
            return;
        }
    }

    private void writeBoard() {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(1L);

        if (categoryList.isEmpty()) {
            System.out.println("=== 생성된 카테고리가 없습니다. 관리자에게 문의해주세요. ===");
            return;
        }

        System.out.println();
        System.out.println("=================================");
        System.out.println("         카테고리 선택");
        System.out.println("=================================");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i).getCategoryName());
        }
        System.out.println((categoryList.size() + 1) + ". 이전으로");
        System.out.print("번호를 입력해주세요 : ");

        int choice = inputInt();
        if (choice == categoryList.size() + 1) return;
        if (choice < 1 || choice > categoryList.size()) {
            System.out.println("유효하지 않은 번호입니다.");
            return;
        }

        Long categoryId = categoryList.get(choice - 1).getCategoryId();

        System.out.println("=== 게시글 작성을 시작합니다. ===");
        System.out.print("제목 입력 : ");
        String title = sc.nextLine();
        System.out.print("내용 입력 : ");
        String content = sc.nextLine();

        BoardDTO dto = new BoardDTO();
        dto.setCategoryId(categoryId);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setCreatorId(UserSession.getLoggedInUser().getUserId());

        boolean success = boardController.registerPost(dto);
        if (success) {
            System.out.println("=== 작성 완료 ===");
        } else {
            System.out.println("작성에 실패했습니다. 다시 시도해주세요.");
        }
    }

    private void myBoardMenu() {
        List<BoardDTO> myBoardList = boardController.findByUser(
                UserSession.getLoggedInUser().getUserId());

        if (myBoardList.isEmpty()) {
            System.out.println("작성한 게시글이 없습니다.");
            return;
        }

        boardOutputView.printBoardList(myBoardList);

        System.out.print("게시글 번호를 선택해주세요 (0: 이전으로) : ");
        long boardId = inputLong();
        if (boardId == 0) return;

        boolean exists = myBoardList.stream()
                .anyMatch(b -> b.getBoardId().equals(boardId));

        if (!exists) {
            System.out.println("내 글 목록에 없는 게시글입니다.");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         내 글 관리");
            System.out.println("=================================");
            System.out.println("1. 수정하기");
            System.out.println("2. 삭제하기");
            System.out.println("3. 이전으로");
            System.out.print("번호를 입력해주세요 : ");

            switch (inputInt()) {
                case 1:
                    System.out.println("=== 게시글 수정을 시작합니다. ===");
                    System.out.print("제목 입력 : ");
                    String title = sc.nextLine();
                    System.out.print("내용 입력 : ");
                    String content = sc.nextLine();

                    BoardDTO dto = new BoardDTO();
                    dto.setBoardId(boardId);
                    dto.setTitle(title);
                    dto.setContent(content);
                    dto.setCreatorId(UserSession.getLoggedInUser().getUserId());

                    boolean updated = boardController.updatePost(dto);
                    if (updated) {
                        System.out.println("=== 수정 완료 ===");
                    } else {
                        System.out.println("수정에 실패했습니다.");
                    }
                    return;

                case 2:
                    boolean deleted = boardController.deletePost(boardId,
                            UserSession.getLoggedInUser().getUserId());
                    if (deleted) {
                        System.out.println("=== 삭제 성공 ===");
                    } else {
                        System.out.println("삭제에 실패했습니다.");
                    }
                    return;

                case 3:
                    return;

                default:
                    System.out.println("1~3까지 다시 선택해주세요.");
            }
        }
    }

    private void createCategory() {
        System.out.println("=== 신규 게시판 카테고리를 생성합니다. ===");
        System.out.print("카테고리 이름 입력 : ");
        String name = sc.nextLine();

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(name);
        dto.setVillageId(1L);
        dto.setCreatorId(UserSession.getLoggedInUser().getUserId());

        boolean success = categoryController.insert(dto,
                UserSession.getLoggedInUser().getRole().name());
        if (success) {
            System.out.println("=== " + name + " 카테고리가 생성되었습니다. ===");
        } else {
            System.out.println("카테고리 생성에 실패했습니다.");
        }
    }

    private void updateCategory() {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(1L);
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

        boolean success = categoryController.update(dto,
                UserSession.getLoggedInUser().getRole().name());
        if (success) {
            System.out.println("=== 카테고리가 수정되었습니다. ===");
        } else {
            System.out.println("카테고리 수정에 실패했습니다.");
        }
    }

    private void deleteCategory() {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(1L);
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

        boolean success = categoryController.delete(dto,
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

    private long inputLong() {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}