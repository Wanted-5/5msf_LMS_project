package com.lms.domain.comment.controller;


import com.lms.domain.comment.dto.CommentDTO;
import com.lms.domain.comment.service.CommentService;

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
}
