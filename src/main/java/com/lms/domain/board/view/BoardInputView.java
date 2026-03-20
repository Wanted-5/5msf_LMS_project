package com.lms.domain.board.view;

import com.lms.domain.board.controller.BoardController;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.category.dto.CategoryDTO;
import com.lms.domain.category.service.CategoryService;

import java.util.List;
import java.util.Scanner;

public class BoardInputView {

    private final BoardController boardController;
    private final BoardOutputView boardOutputView;
    private final CategoryService categoryService;
    private final Scanner sc = new Scanner(System.in);

    public BoardInputView(BoardController boardController, BoardOutputView boardOutputView, CategoryService categoryService) {
        this.boardController = boardController;
        this.boardOutputView = boardOutputView;
        this.categoryService = categoryService;
    }

    public void boardFirstMenu() {
        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("            게시판 메뉴             ");
            System.out.println("=================================");
            System.out.println("1. 게시판 조회");
            System.out.println("2. 게시판 작성");
            System.out.println("3. 내 글 관리");
            System.out.println("4. 메인페이지로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            switch (inputInt()) {
                case 1:
                    searchBoard();
                    break;
                case 2:
                    writeBoard();
                    break;
                case 3:
                    myBoardMenu();
                    break;
                case 4:
                    return;
                default: System.out.println("1~4까지 다시 선택해주세요.");
            }
        }
    }

    private void searchBoard() {
        // DB에서 카테고리 가져옴
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

            // DB category_id 사용
            Long categoryId = categoryList.get(choice - 1).getCategoryId();
            List<BoardDTO> boardList = boardController.findAllBoard(categoryId);
            boardOutputView.printBoardList(boardList);

            if (boardList.isEmpty()) return;

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
        // DB에서 카테고리 가져옴
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

        // DB category_id 사용
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

        boolean success = boardController.registerPost(dto);
        if (success) {
            System.out.println("=== 작성 완료 ===");
        } else {
            System.out.println("작성에 실패했습니다. 다시 시도해주세요.");
        }
    }

    private void myBoardMenu() {
        // 내 글 목록 자동 출력
        List<BoardDTO> myBoardList = boardController.findByUser(6L); // 임시 고정 ID

        if (myBoardList.isEmpty()) {
            System.out.println("작성한 게시글이 없습니다.");
            return;
        }

        boardOutputView.printBoardList(myBoardList);

        // 게시글 번호 선택
        System.out.print("게시글 번호를 선택해주세요 (0: 이전으로) : ");
        long boardId = inputLong();
        if (boardId == 0) return;

        // 선택한 게시글이 내 글 목록에 있는지 확인
        boolean exists = myBoardList.stream()
                .anyMatch(b -> b.getBoardId().equals(boardId));

        if (!exists) {
            System.out.println("내 글 목록에 없는 게시글입니다.");
            return;
        }

        // 수정 / 삭제 메뉴
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
                    // 수정
                    System.out.println("=== 게시글 수정을 시작합니다. ===");
                    System.out.print("제목 입력 : ");
                    String title = sc.nextLine();
                    System.out.print("내용 입력 : ");
                    String content = sc.nextLine();

                    BoardDTO dto = new BoardDTO();
                    dto.setBoardId(boardId);
                    dto.setTitle(title);
                    dto.setContent(content);

                    boolean updated = boardController.updatePost(dto);
                    if (updated) {
                        System.out.println("=== 수정 완료 ===");
                    } else {
                        System.out.println("수정에 실패했습니다.");
                    }
                    return;

                case 2:
                    // 삭제
                    boolean deleted = boardController.deletePost(boardId, 6L);
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