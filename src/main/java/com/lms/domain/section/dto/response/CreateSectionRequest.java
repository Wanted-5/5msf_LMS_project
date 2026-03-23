package com.lms.domain.section.dto.response;

public class CreateSectionRequest {
    private Long villageId;
    private Long userId;
    private int chapNo;
    private String sectionName;
    private String content;
    private String videoUrl;

    public CreateSectionRequest(Long villageId, Long userId, int chapNo, String sectionName, String content, String videoUrl) {
        this.villageId = villageId;
        this.userId = userId;
        this.chapNo = chapNo;
        this.sectionName = sectionName;
        this.content = content;
        this.videoUrl = videoUrl;
    }

    public Long getVillageId() {
        return villageId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getChapNo() {
        return chapNo;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getContent() {
        return content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}