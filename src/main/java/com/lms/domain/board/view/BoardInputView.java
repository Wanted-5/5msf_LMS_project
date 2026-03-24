package com.lms.domain.board.view;

import com.lms.domain.board.controller.BoardController;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.category.controller.CategoryController;
import com.lms.domain.category.dto.CategoryDTO;
import com.lms.domain.category.service.CategoryService;
import com.lms.domain.category.view.CategoryInputView;
import com.lms.domain.comment.view.CommentInputView;
import com.lms.domain.users.dto.UserDTO;
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
    private final CategoryInputView categoryInputView;
    private final CommentInputView commentInputView;


    public BoardInputView(BoardController boardController, BoardOutputView boardOutputView,
                          CategoryService categoryService, CategoryController categoryController, CategoryInputView categoryInputView, CommentInputView commentInputView) {
        this.boardController = boardController;
        this.boardOutputView = boardOutputView;
        this.categoryService = categoryService;
        this.categoryController = categoryController;
        this.categoryInputView = categoryInputView;
        this.commentInputView = commentInputView;
    }

    public void boardFirstMenu(Long villageId) {
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
                System.out.println("4. 카테고리 관리");
            }
            System.out.println("0. 메인페이지로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int input = inputInt();

            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                switch (input) {
                    case 1:
                        searchBoard(villageId);
                        break;
                    case 2:
                        writeBoard(villageId);
                        break;
                    case 3:
                        myBoardMenu();
                        break;
                    case 4:
                        categoryInputView.Categorystart(villageId);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("0 ~ 4까지 다시 선택해주세요.");
                }
            } else {
                switch (input) {
                    case 1:
                        searchBoard(villageId);
                        break;
                    case 2:
                        writeBoard(villageId);
                        break;
                    case 3:
                        myBoardMenu();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("0 ~ 3까지 다시 선택해주세요.");
                }
            }
        }
    }

    private void searchBoard(Long villageId) {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(villageId);

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
            commentInputView.showInitialMenu(boardId);
            return;


        }
    }

    private void writeBoard(Long villageId) {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(villageId);

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

        boolean success = boardController.registerPost(dto, villageId);
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
    private long inputLong() {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
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
}