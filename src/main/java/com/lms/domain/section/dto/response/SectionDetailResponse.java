package com.lms.domain.section.dto.response;

// 강의 상세 조회 항목 담은 Response
public class SectionDetailResponse {
    private Long sectionId;
    private Long userId;
    private int chapNo;
    private String sectionName;
    private String content;
    private String videoUrl;

    public SectionDetailResponse(Long sectionId, Long userId, int chapNo, String sectionName, String content, String videoUrl) {
        this.sectionId = sectionId;
        this.userId = userId;
        this.chapNo = chapNo;
        this.sectionName = sectionName;
        this.content = content;
        this.videoUrl = videoUrl;
    }

    public Long getSectionId() {
        return sectionId;
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
