package com.lms.domain.section.controller;


import com.lms.domain.section.dto.SectionDTO;
import com.lms.domain.section.dto.request.SectionDetailRequest;
import com.lms.domain.section.dto.response.SectionDetailResponse;
import com.lms.domain.section.dto.response.SectionListResponse;
import com.lms.domain.section.service.SectionService;

import java.util.List;

public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    // 강의 전체 조회
    public List<SectionListResponse> displayAllSections(long villageId) {
            return sectionService.findSectionsByVillageId(villageId);
    }

    // 강의 상세 조회
    public SectionDetailResponse displaySectionDetail(long villageId, long sectionId) throws Exception{
        SectionDetailRequest request = new SectionDetailRequest(villageId, sectionId);
        return sectionService.findSectionBySectionId(request);
    }

    public SectionDTO getSectionById(long sectionId) {
        try {
            return sectionService.findSectionById(sectionId);
        } catch (Exception e) {
            System.out.println("강의 상세 조회 중 오류: " + e.getMessage());
            return null;
        }
    }

    public SectionDTO getSectionByVillageIdAndSectionId(long villageId, long sectionId) {
        try {
            return sectionService.findSectionByVillageIdAndSectionId(villageId, sectionId);
        } catch (Exception e) {
            System.out.println("강의 조회 중 오류: " + e.getMessage());
            return null;
        }
    }
}

