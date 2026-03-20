package com.lms.domain.comment.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommentDTO {

    private Long commentId;
    private Long boardId;
    private Long creatorId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentDTO(){}

    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentId=" + commentId +
                ", boardId=" + boardId +
                ", creatorId=" + creatorId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public CommentDTO(Long commentId, Long boardId, Long creatorId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.creatorId = creatorId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CommentDTO(Long boardId, Long creatorId, String content) {
        this.boardId = boardId;
        this.creatorId = creatorId;
        this.content = content;
    }

    public CommentDTO(long commentId, long boardId, String content, Timestamp createdAt, Timestamp updatedAt) {
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
