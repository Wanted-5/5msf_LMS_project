package com.lms.domain.category.dto;

import java.time.LocalDateTime;

public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
    private Long villageId;
    private Long creatorId;
    private LocalDateTime createdAt;
    private String creatorNickname;

    public CategoryDTO(){}

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", villageId=" + villageId +
                ", creatorId='" + creatorId + '\'' +
                ", createdAt=" + createdAt +
                ", creatorNickname='" + creatorNickname + '\'' +
                '}';
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }
}
