package com.lms.domain.section.controller;

import com.lms.domain.section.model.DTO.SectionDTO;
import com.lms.domain.section.model.service.SectionService;

import java.util.List;

public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    public List<SectionDTO> getSectionsByVillageId(long villageId) {
        try {
            return sectionService.findSectionsByVillageId(villageId);
        } catch (Exception e) {
            System.out.println("강의 목록 조회 중 오류: " + e.getMessage());
            return List.of();
        }
    }

    public SectionDTO getSectionById(long sectionId) {
        try {
            return sectionService.findSectionById(sectionId);
        } catch (Exception e) {
            System.out.println("강의 상세 조회 중 오류: " + e.getMessage());
            return null;
        }
    }
}