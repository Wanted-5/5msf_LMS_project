package com.lms.domain.comment.controller;


import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.comment.service.CommentService;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.global.common.UserSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public boolean createComment(long boardId, String content) {

        if (content == null || content.trim().isEmpty()) {
            System.out.println("댓글 내용을 입력하셔야 합니다!");
            return false;
        }

        LoginResponse loginUser = UserSession.getLoggedInUser();

        Long currentUserId = loginUser.getUserId();

        CommentDTO newComment = new CommentDTO(boardId, currentUserId, content);
        return commentService.createComment(newComment);
    }

    public List<CommentDTO> findCommentAll(long boardId) {

        return commentService.findCommentAll(boardId);
    }

    public List<CommentDTO> getEditableComments(long boardId) {
            return commentService.getCommentsForBoard(boardId);
    }

    public boolean updateComment(long commentId, String content) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        CommentDTO updateDto = new CommentDTO();
        updateDto.setCommentId(commentId);
        updateDto.setContent(content);

        return commentService.updateComment(updateDto);
    }
    public boolean deleteComment(long commentId) {

        return commentService.deleteComment(commentId);
    }

}
