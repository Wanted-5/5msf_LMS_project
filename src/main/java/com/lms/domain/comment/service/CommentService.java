package com.lms.domain.comment.service;


import com.lms.domain.comment.dao.CommentDAO;
import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.users.dto.UserRole;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.global.common.UserSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

            System.err.println("댓글 작성 중 DB 에러 발생: " + e.getMessage());
            return false;
        }
    }

    public List<CommentDTO> findCommentAll(long boardId) {

        try {
            List<CommentDTO> commentDTOList = commentDAO.findCommentAll(boardId);

            if (commentDTOList == null || commentDTOList.isEmpty()) {
               throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
            }

            return commentDTOList;

        } catch (SQLException e) {
            throw new RuntimeException("댓글 전체 조회 중 Error 발생!! " + e);
        }
    }
    public List<CommentDTO> getCommentsForBoard(long boardId) {

        LoginResponse loginUser = UserSession.getLoggedInUser();

            try {
                if (loginUser.getRole() == UserRole.ADMIN || loginUser.getRole() == UserRole.INSTRUCTOR) {
                    return commentDAO.findCommentAll(boardId);
                } else {
                    return commentDAO.findUserCommentAll(boardId, loginUser.getUserId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public boolean updateComment(CommentDTO updateDto) {
        try {

            int result = commentDAO.updateComment(updateDto);

            return result > 0;

        } catch (SQLException e) {

            System.out.println("댓글 수정 중 DB 오류: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteComment(long commentId) {
        try {
            int result = commentDAO.deleteComment(commentId);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("댓글 삭제 중 DB 오류: " + e.getMessage());
            return false;
        }
    }
}
