package com.lms.domain.board.dao;

import com.lms.domain.board.dto.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lms.global.util.QueryUtil.getQuery;

public class BoardDAO {

    private final Connection connection;

    public BoardDAO(Connection con) {
        this.connection = con;
    }

    public List<BoardDTO> findAllByCategory(Long categoryId) throws SQLException {
        // 키 이름 변경: board.findAll -> board.findAllByCategory
        String query = getQuery("board.findAllByCategory");
        List<BoardDTO> boardDTOList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, categoryId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                BoardDTO boardDTO = new BoardDTO(
                        rset.getLong("board_id"),
                        rset.getString("title"),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getString("nickname")
                );

                // DTO에 viewCount 필드가 있다면 세팅
                // boardDTO.setViewCount(rset.getInt("view_count"));

                boardDTOList.add(boardDTO);
            }
        }
        return boardDTOList;
    }

    // 게시글 작성
    public int insert(BoardDTO dto, Long villageId) throws SQLException {
        String query = getQuery("board.insert");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, villageId);
            pstmt.setLong(2, dto.getCreatorId());
            pstmt.setLong(3, dto.getCategoryId());
            pstmt.setString(4, dto.getTitle());
            pstmt.setString(5, dto.getContent());
            return pstmt.executeUpdate();
        }
    }

    // 게시글 상세 조회
    public BoardDTO findById(Long boardId) throws SQLException {
        // XML의 key="board.findById"와 반드시 일치해야 함
        String query = getQuery("board.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, boardId);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                // 조회수 필드 없이 제목, 내용, 작성일, 닉네임만 매핑
                return new BoardDTO(
                        rset.getLong("board_id"),
                        rset.getString("title"),
                        rset.getString("content"),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getString("nickname")
                );
            }
        }
        return null;
    }


    // 내 글 전체 조회
    public List<BoardDTO> findByUser(Long creatorId) throws SQLException {
        String query = getQuery("board.findByUser");
        List<BoardDTO> boardDTOList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, creatorId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                BoardDTO boardDTO = new BoardDTO(
                        rset.getLong("board_id"),
                        rset.getString("title"),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getString("nickname")
                );
                boardDTOList.add(boardDTO);
            }
        }
        return boardDTOList;
    }

    // 내 글 수정
    public int update(BoardDTO dto) throws SQLException {
        String query = getQuery("board.update");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setLong(3, dto.getBoardId());
            pstmt.setLong(4, dto.getCreatorId());
            return pstmt.executeUpdate();
        }
    }

    // 내 글 삭제
    public int delete(Long boardId, Long creatorId) throws SQLException {
        // 기존 코드 오류 수정: BOARD_UPDATE -> board.delete
        String query = getQuery("board.delete");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, boardId);
            pstmt.setLong(2, creatorId);
            return pstmt.executeUpdate();
        }
    }
}