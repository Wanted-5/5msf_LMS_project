package com.lms.domain.comment.service;


import com.lms.domain.comment.dao.CommentDAO;
import com.lms.domain.comment.dto.CommentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CommentService {

    private final CommentDAO commentDAO;
    private final Connection connection;

    public CommentService(Connection connection) {
        this.commentDAO = new CommentDAO(connection);
        this.connection = connection;
    }

    public boolean createComment(CommentDTO newComment) {
        try {

            int result = commentDAO.insertComment(newComment);

            return result > 0;

        } catch (SQLException e) {

            System.err.println("🚨 댓글 작성 중 DB 에러 발생: " + e.getMessage());
            return false;
        }
    }


}
