package com.lms.domain.board.controller;

import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.service.BoardService;

import java.util.List;

public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 카테고리별 게시글 전체 조회
    public List<BoardDTO> findAllBoard(Long categoryId) {
        return boardService.findAll(categoryId);
    }

    // 게시글 작성
    public boolean registerPost(BoardDTO dto) {
        // 디버깅 출력용 System.out.println("dto = " + dto);
        dto.setVillageId(1L); // 임시방편 연동하면 서비스로 옮겨야대요
        return boardService.registerPost(dto);
    }
    // 게시글 상세 조회
    public BoardDTO findById(Long boardId) {
        return boardService.findById(boardId);
    }

    // 내 글 전체 조회
    public List<BoardDTO> findByUser(Long creatorId) {
        return boardService.findByUser(creatorId);
    }

    // 내 글 수정
    public boolean updatePost(BoardDTO dto) {
        return boardService.updatePost(dto);
    }

    // 내 글 삭제
    public boolean deletePost(Long boardId, Long creatorId) {
        return boardService.deletePost(boardId, creatorId);
    }
}