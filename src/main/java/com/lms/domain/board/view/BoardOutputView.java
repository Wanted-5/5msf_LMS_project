package com.lms.domain.board.view;

import com.lms.domain.board.dto.BoardDTO;

import java.util.List;

public class BoardOutputView {

    // 게시글 목록 출력
    public void printBoardList(List<BoardDTO> boardList) {
        if (boardList.isEmpty()) {
            System.out.println("등록된 게시글이 없습니다.");
            return;
        }
        System.out.println("=================================");
        System.out.println("         게시글 목록");
        System.out.println("=================================");
        for (BoardDTO board : boardList) {
            System.out.printf("[%d] %s | %s | %s%n",
                    board.getBoardId(),
                    board.getTitle(),
                    board.getNickname(),
                    board.getCreatedAt()
            );
        }
        System.out.println("=================================");
    }

    // 카테고리 선택 메뉴 출력
    public void printCategoryMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("         카테고리 선택");
        System.out.println("=================================");
        System.out.println("1. 자유 게시판");
        System.out.println("2. QnA 게시판");
        System.out.println("3. 공지 게시판");
        System.out.println("4. 문의 게시판");
        System.out.println("5. 이전으로");
        System.out.print("번호를 입력해주세요 : ");
    }
    // 게시글 상세 출력
    public void printBoardDetail(BoardDTO board) {
        System.out.println("=================================");
        System.out.println("         게시글 상세 조회");
        System.out.println("=================================");
        System.out.println("제목 : " + board.getTitle());
        System.out.println("작성자 : " + board.getNickname());
        System.out.println("작성일 : " + board.getCreatedAt());
        System.out.println("---------------------------------");
        System.out.println("내용 : " + board.getContent());
        System.out.println("=================================");
    }
}