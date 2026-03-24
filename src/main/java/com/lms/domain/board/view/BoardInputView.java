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
            // 🌟 1. 권한 확인을 boolean 변수로 분리하여 가독성 향상
            UserRole role = UserSession.getLoggedInUser().getRole();
            boolean isManager = (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN);

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 📢 마을 커뮤니티 (자유 게시판)                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 마을 주민들과 지식을 나누고 자유롭게 소통하는 공간입니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📋 게시판 전체 조회");
            System.out.println("      [ 2 ] ✍️ 새로운 게시글 작성");
            System.out.println("      [ 3 ] 🗂️ 내 게시글 관리");

            // 관리자/강사 전용 메뉴 렌더링
            if (isManager) {
                System.out.println("      [ 4 ] 🛠️ 게시판 카테고리 관리 (강사/관리자 전용)");
            }
            System.out.println("      [ 0 ] 🚪 메인페이지(마을 광장)로 돌아가기");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 메뉴의 번호를 입력해주세요 : ");

            int input = inputInt();

            if (isManager) {
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
                        System.out.println("\n  [ 시스템 ] 카테고리 관리 시스템에 접속합니다...");
                        categoryInputView.Categorystart(villageId);
                        break;
                    case 0:
                        System.out.println("\n  [ 시스템 ] 게시판을 떠나 광장으로 돌아갑니다.");
                        return;
                    default:
                        System.out.println("\n  🚨 [경고] 올바른 번호(0 ~ 4)를 선택해주세요.");
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
                        System.out.println("\n  [ 시스템 ] 게시판을 떠나 광장으로 돌아갑니다.");
                        return;
                    default:
                        System.out.println("\n  🚨 [경고] 올바른 번호(0 ~ 3)를 선택해주세요.");
                }
            }
        }
    }

    private void searchBoard(Long villageId) {
        System.out.println("\n  [ 시스템 ] 마을의 게시판 카테고리 목록을 동기화합니다...");
        List<CategoryDTO> categoryList = categoryService.getCategoryList(villageId);

        if (categoryList.isEmpty()) {
            System.out.println("\n  🚨 [안내] 현재 생성된 카테고리가 없습니다. 강사/관리자에게 문의해주세요.");
            return;
        }

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 🗂️ 마을 게시판 카테고리 선택                     ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 열람하고자 하는 게시판의 카테고리를 선택해 주세요.");
            System.out.println("────────────────────────────────────────────────────────────────");

            for (int i = 0; i < categoryList.size(); i++) {
                System.out.println("      [ " + (i + 1) + " ] 📂 " + categoryList.get(i).getCategoryName());
            }

            int goBackNumber = categoryList.size() + 1;
            System.out.println("      [ " + goBackNumber + " ] ↩️ 이전 메뉴로 돌아가기");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 번호를 입력해주세요 : ");

            int choice = inputInt();

            if (choice == goBackNumber) {
                System.out.println("\n  [ 시스템 ] 이전 메뉴로 돌아갑니다.");
                return;
            }

            if (choice < 1 || choice > goBackNumber) {
                System.out.println("\n  🚨 [경고] 유효하지 않은 번호입니다. 다시 선택해주세요.");
                continue;
            }

            Long categoryId = categoryList.get(choice - 1).getCategoryId();
            String categoryName = categoryList.get(choice - 1).getCategoryName();

            System.out.println("\n  [ 시스템 ] [ " + categoryName + " ] 카테고리의 게시글을 불러옵니다...");
            List<BoardDTO> boardList = boardController.findAllBoard(categoryId);

            boardOutputView.printBoardList(boardList);

            if (boardList.isEmpty()) {
                continue;
            }

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 상세 열람할 게시글 번호를 입력해주세요 (취소: 0) : ");
            long boardId = inputLong();

            if (boardId == 0) {
                System.out.println("\n  [ 시스템 ] 상세 열람을 취소하고 카테고리 선택으로 돌아갑니다.");
                continue;
            }

            System.out.println("\n  [ 시스템 ] 게시글 상세 정보를 암호화 해제하여 불러옵니다...");
            BoardDTO board = boardController.findById(boardId);

            if (board == null) {
                System.out.println("\n  🚨 [오류] 존재하지 않거나 삭제된 게시글입니다.");
                continue;
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
        long currentUserId = UserSession.getLoggedInUser().getUserId();
        List<BoardDTO> myBoardList = boardController.findByUser(currentUserId);

        if (myBoardList.isEmpty()) {
            System.out.println("\n  [ 안내 ] 현재까지 마을 게시판에 작성하신 기록이 존재하지 않습니다.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🗂️ 본인 작성 게시글 관리 모드                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 관리자님이 작성하신 게시글 목록을 데이터베이스에서 동기화했습니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        boardOutputView.printBoardList(myBoardList);

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 관리(수정/삭제)할 게시글 번호를 선택해주세요 (취소: 0) : ");
        long boardId = inputLong();

        if (boardId == 0) {
            System.out.println("\n  [ 시스템 ] 관리 모드를 종료하고 이전 메뉴로 돌아갑니다.");
            return;
        }

        boolean exists = myBoardList.stream()
                .anyMatch(b -> b.getBoardId().equals(boardId));

        if (!exists) {
            System.out.println("\n  🚨 [경고] 본인이 작성한 글 목록에 존재하지 않는 번호입니다. 접근이 제한됩니다.");
            return;
        }

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 ⚙️ 선택된 게시글 제어 패널                       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("      [ 1 ] 📝 게시글 수정 (데이터 리모델링)");
            System.out.println("      [ 2 ] 🗑️ 게시글 삭제 (데이터 영구 철거)");
            System.out.println("      [ 3 ] ↩️ 관리 취소 및 이전으로");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 실행할 업무 번호를 입력해주세요 : ");

            int choice = inputInt();

            switch (choice) {
                case 1:
                    System.out.println("\n  [ 시스템 ] 게시글 수정 프로세스를 가동합니다.");
                    System.out.println("  ──────────────────────────────────────────────────────────");
                    System.out.print("  ▶ 변경할 새로운 제목 : ");
                    String title = sc.nextLine().trim();
                    System.out.print("  ▶ 변경할 새로운 내용 : ");
                    String content = sc.nextLine().trim();

                    BoardDTO updateDto = new BoardDTO();
                    updateDto.setBoardId(boardId);
                    updateDto.setTitle(title);
                    updateDto.setContent(content);
                    updateDto.setCreatorId(currentUserId);

                    System.out.println("\n  [ 시스템 ] 서버에 수정된 데이터를 전송하고 업데이트 중입니다...");
                    boolean updated = boardController.updatePost(updateDto);

                    if (updated) {
                        System.out.println("  🎉 [ 성공 ] 게시글이 안전하게 리모델링 되었습니다.");
                    } else {
                        System.out.println("  🚨 [ 실패 ] 서버 통신 오류로 인해 수정에 실패했습니다.");
                    }
                    return;

                case 2:
                    System.out.println("\n  🚨 [주의] 삭제된 데이터는 복구할 수 없습니다. 정말 철거하시겠습니까?");
                    System.out.print("  ▶ 동의하시면 'Y' / 'y'를 입력해 주세요 (취소는 아무 키) : ");
                    String confirm = sc.nextLine().trim();

                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("\n  [ 시스템 ] 게시글 철거 작업을 시작합니다...");
                        boolean deleted = boardController.deletePost(boardId, currentUserId);
                        if (deleted) {
                            System.out.println("  🎉 [ 성공 ] 해당 게시글이 데이터베이스에서 완전히 삭제되었습니다.");
                        } else {
                            System.out.println("  🚨 [ 실패 ] 삭제 권한이 없거나 서버 오류가 발생했습니다.");
                        }
                    } else {
                        System.out.println("\n  [ 시스템 ] 삭제 요청이 취소되었습니다. 관제실로 복귀합니다.");
                    }
                    return;

                case 3:
                    System.out.println("\n  [ 시스템 ] 관리 모드를 빠져나갑니다.");
                    return;

                default:
                    System.out.println("\n  🚨 [경고] 존재하지 않는 업무 번호입니다. (1~3 사이 선택)");
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