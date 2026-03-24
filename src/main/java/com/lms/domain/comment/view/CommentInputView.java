package com.lms.domain.comment.view;


import com.lms.domain.comment.controller.CommentController;
import com.lms.domain.comment.dto.CommentDTO;

import java.util.List;
import java.util.Scanner;

public class CommentInputView {

    private final CommentController commentController;
    private final CommentOutputView commentOutputView;

    public CommentInputView(CommentController commentController, CommentOutputView commentOutputView) {
        this.commentController = commentController;
        this.commentOutputView = commentOutputView;
    }

    Scanner sc = new Scanner(System.in);



    public void showInitialMenu(Long boardId) {

        this.CommentMenu(boardId);
    }

    public void CommentMenu(long boardId) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 💬 마을 게시판 - 댓글 관리 통제실                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("  [ 시스템 ] 해당 게시글에 대한 주민들의 의견을 관리하는 공간입니다.");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("      [ 1 ] 📋 댓글 전체 조회 (의견 열람)");
            System.out.println("      [ 2 ] ✍️ 새로운 댓글 작성 (의견 게시)");
            System.out.println("      [ 3 ] ⚙️ 댓글 수정 (의견 리모델링)");
            System.out.println("      [ 4 ] 🗑️ 댓글 삭제 (의견 철거)");
            System.out.println("      [ 5 ] ↩️ 게시글 상세 화면으로 복귀");
            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.print("  ▶ 원하시는 업무의 번호를 입력해주세요 : ");

            int menu;
            try {
                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n  🚨 [경고] 업무 번호는 숫자만 입력 가능합니다.");
                continue;
            }

            switch (menu) {
                case 1:
                    System.out.println("\n  [ 시스템 ] 현재 게시글에 달린 모든 댓글을 불러옵니다...");
                    selectComment(boardId);
                    break;
                case 2:
                    System.out.println("\n  [ 시스템 ] 새로운 의견을 작성합니다. 타인을 배려하는 언어를 사용해 주세요.");
                    writeComment(boardId);
                    break;
                case 3:
                    System.out.println("\n  [ 시스템 ] 기존에 작성한 댓글을 수정합니다.");
                    updateComment(boardId);
                    break;
                case 4:
                    System.out.println("\n  [ 시스템 ] 댓글 삭제 프로세스를 가동합니다.");
                    deleteComment(boardId);
                    break;
                case 5:
                    System.out.println("\n  [ 시스템 ] 댓글 관리를 종료하고 게시글 상세 화면으로 복귀합니다.");
                    return;
                default:
                    commentOutputView.printError("올바른 업무 번호(1~5)를 입력해 주세요.");
            }
        }
    }

    private void selectComment(long boardId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 📋 마을 게시판 - 전체 댓글(의견) 열람             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 해당 게시글에 등록된 모든 주민의 의견을 동기화합니다.");
        System.out.println("────────────────────────────────────────────────────────────────");

        List<CommentDTO> commentList = commentController.findCommentAll(boardId);

        if (commentList == null || commentList.isEmpty()) {
            System.out.println("  [ 안내 ] 아직 등록된 의견이 없습니다. 첫 번째 목소리를 내보세요!");
        } else {
            commentOutputView.printComment(commentList);
        }

        System.out.println("────────────────────────────────────────────────────────────────\n");
    }

    private void deleteComment(long boardId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 🗑️ 마을 게시판 - 댓글 삭제 (철거)                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 본인이 작성한 의견만 철거할 수 있습니다. 데이터를 조회합니다...");

        // 핵심 로직 유지
        List<CommentDTO> deleteComments = commentController.getEditableComments(boardId);

        if (deleteComments == null || deleteComments.isEmpty()) {
            commentOutputView.printError("삭제(철거)할 수 있는 본인의 댓글이 존재하지 않습니다.");
            return;
        }

        System.out.println("────────────────────────────────────────────────────────────────");
        commentOutputView.printMessage("📂 [ 철거 가능한 내 댓글 목록 ]");
        commentOutputView.printComment(deleteComments);
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.print("  ▶ 철거할 댓글의 순번( [ ] 안의 번호 )을 선택하세요 : ");
        int selectNum = (int) inputLong();

        if (selectNum < 1 || selectNum > deleteComments.size()) {
            commentOutputView.printError("잘못된 번호입니다. 화면에 보이는 순번을 정확히 입력해주세요.");
            return;
        }

        CommentDTO selectedComment = deleteComments.get(selectNum - 1);
        long realCommentId = selectedComment.getCommentId();

        System.out.println("\n  [ 시스템 ] 선택하신 의견을 마을 게시판에서 완전히 철거합니다...");
        boolean isSuccess = commentController.deleteComment(realCommentId);

        if (isSuccess) {
            commentOutputView.printSuccess("해당 댓글(의견)이 영구적으로 철거되었습니다.");
        } else {
            commentOutputView.printError("서버 오류 및 권한 문제로 댓글 철거에 실패했습니다.");
        }
    }

    private void updateComment(long boardId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ⚙️ 마을 게시판 - 댓글 수정 (리모델링)             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 본인이 작성한 의견만 수정할 수 있습니다. 데이터를 조회합니다...");

        // 핵심 로직 유지
        List<CommentDTO> editableComments = commentController.getEditableComments(boardId);

        if (editableComments == null || editableComments.isEmpty()) {
            commentOutputView.printError("수정(리모델링)할 수 있는 본인의 댓글이 존재하지 않습니다.");
            return;
        }

        System.out.println("────────────────────────────────────────────────────────────────");
        commentOutputView.printMessage("📂 [ 수정 가능한 내 댓글 목록 ]");
        commentOutputView.printComment(editableComments);
        System.out.println("────────────────────────────────────────────────────────────────");

        System.out.print("  ▶ 수정할 댓글의 순번( [ ] 안의 번호 )을 선택하세요 : ");
        int selectNum = (int) inputLong();

        if (selectNum < 1 || selectNum > editableComments.size()) {
            commentOutputView.printError("잘못된 번호입니다. 화면에 보이는 순번을 정확히 입력해주세요.");
            return;
        }

        CommentDTO selectedComment = editableComments.get(selectNum - 1);
        long realCommentId = selectedComment.getCommentId();

        System.out.println("  ──────────────────────────────────────────────────────────");
        System.out.print("  ▶ 변경할 새로운 의견(댓글) 내용을 입력해주세요 : ");
        String content = sc.nextLine().trim();

        System.out.println("\n  [ 시스템 ] 서버에 수정된 데이터를 전송하고 업데이트 중입니다...");
        boolean isSuccess = commentController.updateComment(realCommentId, content);

        if (isSuccess) {
            commentOutputView.printSuccess("해당 댓글(의견)이 성공적으로 리모델링 되었습니다.");
        } else {
            commentOutputView.printError("서버 오류 및 권한 문제로 댓글 수정에 실패했습니다.");
        }
    }

    private void writeComment(long boardId) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ✍️ 마을 게시판 - 새로운 의견 게시                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  [ 시스템 ] 타인을 배려하는 따뜻한 언어로 마을에 의견을 남겨주세요.");
        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.print("  ▶ 작성할 댓글 내용을 입력해주세요 : ");

        String content = sc.nextLine().trim();

        if (content.isEmpty()) {
            System.out.println("\n  🚨 [경고] 내용이 비어있습니다. 작성을 취소하고 이전 메뉴로 돌아갑니다.");
            return;
        }

        System.out.println("\n  [ 시스템 ] 작성하신 의견을 서버로 전송합니다...");
        boolean isSuccess = commentController.createComment(boardId, content);

        if (isSuccess) {
            commentOutputView.printSuccess("새로운 의견(댓글)이 마을 게시판에 성공적으로 등록되었습니다.");
        } else {
            commentOutputView.printError("서버 오류로 인해 의견 등록에 실패했습니다. 다시 시도해 주세요.");
        }
    }

    // ==================== 내부 편의 메서드 ==========================
    private long inputLong() {
        while (true) {
            try {
                long value = Long.parseLong(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}
