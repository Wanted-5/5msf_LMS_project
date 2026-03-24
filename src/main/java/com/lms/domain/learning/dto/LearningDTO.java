package com.lms.domain.learning.dto;

import java.time.LocalDateTime;

public class LearningDTO {

    private Long learningId;
    private Long userId;
    private Long villageId;
    private Long sectionId;
    private LearningStatus status;
    private LocalDateTime createdAt;

    public LearningDTO(Long learningId, Long userId, Long villageId, Long sectionId, LearningStatus status, LocalDateTime createdAt) {
        this.learningId = learningId;
        this.userId = userId;
        this.villageId = villageId;
        this.sectionId = sectionId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getLearningId() {
        return learningId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public LearningStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
