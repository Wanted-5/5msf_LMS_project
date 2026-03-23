package com.lms.domain.board.service;

import com.lms.domain.board.dao.BoardDAO;
import com.lms.domain.board.dto.BoardDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BoardService {

    private final BoardDAO boardDAO;
    private final Connection con;

    public BoardService(Connection con) {
        this.boardDAO = new BoardDAO(con);
        this.con = con;
    }

    // 게시글 작성
    public boolean registerPost(BoardDTO dto, Long villageId) {
       // 디버깅용  System.out.println("dto = " + dto);
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            System.out.println("오류: 제목을 입력해주세요.");
            return false;
        }
        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            System.out.println("오류: 내용을 입력해주세요.");
            return false;
        }
        try {
            // 디버깅 확인용 System.out.println("dadadadadada");
            int result = boardDAO.insert(dto, villageId); //
           //디버깅 확인용 System.out.println("여기 동작하니?");
            return result > 0;
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            return false;
        }
    }

    // 게시글 전체 조회
    public List<BoardDTO> findAll(Long categoryId) {
        try {
            return boardDAO.findAllByCategory(categoryId);
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            return List.of(); // 예외 시 빈 리스트 반환null 반환하면 나중에
            // NullPointerException 날 수 있어서 빈 리스트로 반환하는것이다.
        }
    }

    // 게시글 상세 조회
    public BoardDTO findById(Long boardId) {
        try {
            return boardDAO.findById(boardId);
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            return null;
        }
    }
    // 내 글 전체 조회
    public List<BoardDTO> findByUser(Long creatorId) {
        try {
            return boardDAO.findByUser(creatorId);
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            return List.of();
        }
    }

    // 내 글 수정
    public boolean updatePost(BoardDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            System.out.println("오류: 제목을 입력해주세요.");
            return false;
        }
        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            System.out.println("오류: 내용을 입력해주세요.");
            return false;
        }
        try {
            int result = boardDAO.update(dto);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            return false;
        }
    }

    // 내 글 삭제
    public boolean deletePost(Long boardId, Long creatorId) {
        try {
            int result = boardDAO.delete(boardId, creatorId);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
            return false;
        }
    }
}