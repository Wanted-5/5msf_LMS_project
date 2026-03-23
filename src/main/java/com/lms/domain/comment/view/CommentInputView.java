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



    public void showInitialMenu() {
        System.out.print("이동할 게시글 번호를 입력하세요: ");
        long boardId = Long.parseLong(sc.nextLine());

        this.CommentMenu(boardId);
    }

    public void CommentMenu(long boardId) {
        while (true) {
            System.out.println("====댓글 메뉴====");
            System.out.println("1. 댓글 조회하기");
            System.out.println("2. 댓글 작성하기");
            System.out.println("3. 댓글 수정하기");
            System.out.println("4. 댓글 삭제하기");
            System.out.println("5. 이전으로");
            System.out.print("메뉴를 선택해주세요 : ");

            int menu = Integer.parseInt(sc.nextLine());

            switch (menu) {
                case 1:
                    selectComment(boardId);
                    break;
                case 2:
                    writeComment(boardId);
                    break;
                case 3:
                    updateComment(boardId);
                    break;
                case 4:
                    deleteComment(boardId);
                    break;
                case 5:
                    System.out.println("게시글 상세 화면으로 돌아갑니다.");
                    return;
                default:
                    commentOutputView.printError("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    private void selectComment(long boardId) {
        commentOutputView.printMessage("====전체 조회====");

        List<CommentDTO> commentList= commentController.findCommentAll(boardId);

        commentOutputView.printComment(commentList);
    }

    private void deleteComment(long boardId) {
        System.out.println("\n==== 댓글 삭제 ====");

        List<CommentDTO> deletComments = commentController.getEditableComments(boardId);

        if (deletComments == null || deletComments.isEmpty()) {
            commentOutputView.printError("삭제할 수 있는 댓글이 없습니다.");
            return;
        }

        commentOutputView.printMessage("[삭제 가능한 댓글 목록]");
        commentOutputView.printComment(deletComments);

        //구분을 위해 []로 묶어서 출력 -> outputview 출력형태
        System.out.print("\n삭제할 순번( [ ] 안의 번호 )을 선택하세요 : ");
        //인덱스 순번 호출을 위한 형변환(int만 가능하다고 함)
        int selectNum = (int) inputLong();

        if (selectNum < 1 || selectNum > deletComments.size()) {
            commentOutputView.printError("잘못된 번호입니다. 화면에 보이는 순번을 입력해주세요.");
            return;
        }

        CommentDTO selectedComment = deletComments.get(selectNum - 1);
        long realCommentId = selectedComment.getCommentId();

        boolean isSuccess = commentController.deleteComment(realCommentId);

        if (isSuccess) {
            commentOutputView.printSuccess("댓글이 영구적으로 삭제되었습니다.");
        } else {
            commentOutputView.printError("댓글 삭제에 실패했습니다.");
        }
    }

    private void updateComment(long boardId) {
        System.out.println("\n==== 댓글 수정 ====");

        List<CommentDTO> editableComments = commentController.getEditableComments(boardId);

        if (editableComments == null || editableComments.isEmpty()) {
            commentOutputView.printError("수정할 수 있는 댓글이 없습니다.");
            return;
        }

        commentOutputView.printMessage("[수정 가능한 댓글 목록]");
        commentOutputView.printComment(editableComments);

        //구분을 위해 []로 묶어서 출력 -> outputview 출력형태
        System.out.print("\n수정할 순번( [ ] 안의 번호 )을 선택하세요 : ");
        //인덱스 순번 호출을 위한 형변환(int만 가능하다고 함)
        int selectNum = (int) inputLong();

        if (selectNum < 1 || selectNum > editableComments.size()) {
            commentOutputView.printError("잘못된 번호입니다. 화면에 보이는 순번을 입력해주세요.");
            return;
        }

        CommentDTO selectedComment = editableComments.get(selectNum - 1);
        long realCommentId = selectedComment.getCommentId();

        System.out.print("새로운 댓글 내용을 입력해주세요 : ");
        String content = sc.nextLine();

        boolean isSuccess = commentController.updateComment(realCommentId, content);

        if (isSuccess) {
            commentOutputView.printSuccess("댓글이 성공적으로 수정되었습니다.");
        } else {
            commentOutputView.printError("댓글 수정에 실패했습니다.");
        }
    }

    private void writeComment(long boardId) {

        System.out.print("댓글을 입력해주세요 : ");
        String content = sc.nextLine();

        boolean isSuccess = commentController.createComment(boardId, content);

        if (isSuccess) {
            commentOutputView.printSuccess("댓글이 등록되었습니다.");
        } else {
            commentOutputView.printError("댓글 등록에 실패했습니다.");
        }
    }
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
