package com.lms.domain.board.dto;

import com.lms.global.common.UserSession;

import java.time.LocalDateTime;

public class BoardDTO {

    private final Long TEST_USER_ID = 6L;


    private Long boardId;
    private Long villageId;
    private Long creatorId;
    private Long categoryId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;

    // 기본 생성자
    public BoardDTO() {
    }

    public BoardDTO(Long boardId, Long villageId, Long creatorId, Long categoryId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String nickname) {
        this.boardId = boardId;
        this.villageId = villageId;
        this.creatorId = creatorId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.nickname = nickname;
    }
    // findAll용 생성자
    public BoardDTO(Long boardId, String title, LocalDateTime createdAt, String nickname) {
        this.boardId = boardId;
        this.title = title;
        this.createdAt = createdAt;
        this.nickname = nickname;
    }

    // findById용 생성자
    public BoardDTO(Long boardId, String title, String content, LocalDateTime createdAt, String nickname) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.nickname = nickname;
    }

    public Long getTEST_USER_ID() {
        return TEST_USER_ID;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getNickname() {

        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "TEST_USER_ID=" + TEST_USER_ID +
                ", boardId=" + boardId +
                ", villageId=" + villageId +
                ", creatorId=" + creatorId +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
