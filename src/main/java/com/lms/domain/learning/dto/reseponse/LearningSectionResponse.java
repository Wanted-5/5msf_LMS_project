package com.lms.domain.learning.dto.reseponse;

import com.lms.domain.learning.dto.LearningStatus;

public class LearningSectionResponse {
    private Long learningId;
    private Long sectionId;
    private int chapNo;
    private String sectionName;
    private LearningStatus status;

    public LearningSectionResponse(Long learningId, Long sectionId, int chapNo, String sectionName, LearningStatus status) {
        this.learningId = learningId;
        this.sectionId = sectionId;
        this.chapNo = chapNo;
        this.sectionName = sectionName;
        this.status = status;
    }

    public Long getLearningId() {
        return learningId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public int getChapNo() {
        return chapNo;
    }

    public String getSectionName() {
        return sectionName;
    }

    public LearningStatus getStatus() {
        return status;
    }
}
