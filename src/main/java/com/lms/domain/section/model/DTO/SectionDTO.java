package com.lms.domain.section.model.DTO;

public class SectionDTO {
    private long sectionId;
    private long villageId;
    private int chapNo;
    private String sectionName;
    private String content;
    private String videoUrl;
    private String status;

    public long getSectionId() { return sectionId; }
    public void setSectionId(long sectionId) { this.sectionId = sectionId; }

    public long getVillageId() { return villageId; }
    public void setVillageId(long villageId) { this.villageId = villageId; }

    public int getChapNo() { return chapNo; }
    public void setChapNo(int chapNo) { this.chapNo = chapNo; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}