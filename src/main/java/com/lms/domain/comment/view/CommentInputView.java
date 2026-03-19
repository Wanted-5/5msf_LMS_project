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
            System.out.println("1. 댓글 작성하기");
            System.out.println("2. 댓글 수정하기");
            System.out.println("3. 댓글 삭제하기");
            System.out.println("4. 이전으로");
            System.out.print("메뉴를 선택해주세요 : ");

            int menu = Integer.parseInt(sc.nextLine());

            switch (menu) {
                case 1:
                    writeComment(boardId);
                    break;
                case 2:
                    updateComment(boardId);
                    break;
                case 3:
                    deleteComment(boardId);
                    break;
                case 4:
                    System.out.println("게시글 상세 화면으로 돌아갑니다.");
                    return;
                default:
                    commentOutputView.printError("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    private void deleteComment(long boardId) {
    }

    private void updateComment(long boardId) {
//        commentOutputView.printSuccess("==== 강좌 목록 전체 조회 ====");
//
//        List<CommentDTO> commentList = CommentController.findAllcomment();
//
//        commentOutputView.printSuccess(commentList);

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


}
