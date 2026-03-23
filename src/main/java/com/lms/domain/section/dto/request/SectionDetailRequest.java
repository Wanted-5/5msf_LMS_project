package com.lms.domain.section.dto.request;

public class SectionDetailRequest {
    private Long villageId;
    private Long sectionId;

    public SectionDetailRequest(Long villageId, Long sectionId) {
        this.villageId = villageId;
        this.sectionId = sectionId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public Long getSectionId() {
        return sectionId;
    }
}
