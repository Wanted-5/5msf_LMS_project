package com.lms.domain.comment.dao;

import com.lms.domain.comment.dto.CommentDTO;
import com.lms.global.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private final Connection connection;

    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    //댓글 작성
    public int insertComment(CommentDTO dto) throws SQLException {

        String query = QueryUtil.getQuery("comment.target");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, dto.getBoardId());
            pstmt.setLong(2, dto.getCreatorId());
            pstmt.setString(3, dto.getContent());

            return pstmt.executeUpdate();
        }

    }

    //댓글 사용자 권한 조회
    public List<CommentDTO> findUserCommentAll(long boardId, long userId) throws SQLException {
        String query = QueryUtil.getQuery("comment.userselect");
        List<CommentDTO> commentuserList = new ArrayList<>();

        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, boardId);
            pstmt.setLong(2, userId);

            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                commentuserList.add(new CommentDTO(
                        rset.getLong("comment_id"),
                        rset.getLong("board_id"),
                        rset.getLong("creator_id"),
                        rset.getString("content"),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        }
        return commentuserList;
    }

    //댓글 수정
    public int updateComment(CommentDTO dto) throws SQLException {

        String query = QueryUtil.getQuery("comment.update");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, dto.getContent());
            pstmt.setLong(2, dto.getCommentId());

            return pstmt.executeUpdate();
        }
    }

//댓글 삭제
    public int deleteComment(long id) throws SQLException{
        String query = QueryUtil.getQuery("comment.delete");

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, id);

            return pstmt.executeUpdate();
        }
    }

//댓글 전체 조회
    public List<CommentDTO> findCommentAll(long boardId) throws SQLException {

        String query = QueryUtil.getQuery("comment.adminselect");
        List<CommentDTO> commentList = new ArrayList<>();

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setLong(1, boardId);
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
                CommentDTO comment = new CommentDTO(
                        rset.getLong("comment_id"),
                        rset.getLong("board_id"),
                        rset.getLong("creator_id"),
                        rset.getString("content"),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getTimestamp("updated_at").toLocalDateTime()
                );
                commentList.add(comment);
            }

        }
        return commentList;

    }


}
