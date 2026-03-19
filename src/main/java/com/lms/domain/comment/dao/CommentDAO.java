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

    public int insertComment(CommentDTO dto) throws SQLException {

        String query = QueryUtil.getQuery("comment.target");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, dto.getBoardId());
            pstmt.setLong(2, dto.getCreatorId());
            pstmt.setString(3, dto.getContent());

            return pstmt.executeUpdate();
        }

    }

    public List<CommentDTO> findadminAll() throws SQLException {

        String query = QueryUtil.getQuery("comment.adminselect");
        List<CommentDTO> commentList = new ArrayList<>();

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
                CommentDTO comment = new CommentDTO(
                        rset.getLong("comment_id"),
                        rset.getLong("board_id"),
                        rset.getString("content"),
                        rset.getTimestamp("created_at"),
                        rset.getTimestamp("updated_at")
                );
                commentList.add(comment);
            }

        }
        return commentList;

    }

    public List<CommentDTO> findsuerAll() throws SQLException {

        String query = QueryUtil.getQuery("comment.userselect");
        List<CommentDTO> commentuserList = new ArrayList<>();

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
                CommentDTO comment = new CommentDTO(
                        rset.getLong("comment_id"),
                        rset.getLong("board_id"),
                        rset.getString("content"),
                        rset.getTimestamp("created_at"),
                        rset.getTimestamp("updated_at")
                );
                commentuserList.add(comment);
            }

        }
        return commentuserList;

    }

    public void updateComment(){

    }

}
