package com.lms.domain.section.dto.response;

public class SectionListResponse {
    private final Long sectionId;
    private final int chapNo;
    private final String sectionName;
    private final String status;

    public SectionListResponse(Long sectionId, int chapNo, String sectionName, String status) {
        this.sectionId = sectionId;
        this.chapNo = chapNo;
        this.sectionName = sectionName;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
}
