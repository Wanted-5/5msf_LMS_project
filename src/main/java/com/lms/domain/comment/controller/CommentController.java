package com.lms.domain.comment.controller;


import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.comment.service.CommentService;

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
            System.out.println("🚨 댓글 내용을 입력하셔야 합니다!");
            return false;
        }

        Long currentUserId = 1L;
        CommentDTO newComment = new CommentDTO(boardId, currentUserId, content);
        return commentService.createComment(newComment);
    }

    public List<CommentDTO> findCommentAll(long boardId) {

        return commentService.findCommentAll(boardId);
    }

    public List<CommentDTO> getEditableComments(long boardId) {
       // return commentService.findCommentAll(boardId);
        //return commentService.findCommentAll(boardId); 얘는 나중에 지우기
        try {
            //지우면 안되는 권한검증 로직
            return commentService.getCommentsForBoard(boardId);

        } catch (SQLException e) {
            System.out.println("🚨 권한 확인 중 오류 발생: " + e.getMessage());
            return new ArrayList<>();
        }
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
