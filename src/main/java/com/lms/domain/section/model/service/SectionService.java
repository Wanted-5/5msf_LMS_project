package com.lms.domain.section.model.service;

import com.lms.domain.section.model.DAO.SectionDAO;
import com.lms.domain.section.model.DTO.SectionDTO;

import java.sql.Connection;
import java.util.List;

public class SectionService {

    private final Connection con;
    private final SectionDAO sectionDAO;

    public SectionService(Connection con, SectionDAO sectionDAO) {
        this.con = con;
        this.sectionDAO = sectionDAO;
    }

    public List<SectionDTO> findSectionsByVillageId(long villageId) {
        return sectionDAO.findSectionsByVillageId(con, villageId);
    }

    public SectionDTO findSectionById(long sectionId) {
        return sectionDAO.findSectionById(con, sectionId);
    }

    public SectionDTO findSectionByVillageIdAndSectionId(long villageId, long sectionId) {
        return sectionDAO.findSectionByVillageIdAndSectionId(con, villageId, sectionId);
    }
    public void createSection(Long villageId, int chapNo, String sectionName, String content, String videoUrl) {
        if (sectionName == null || sectionName.isBlank()) {
            throw new IllegalArgumentException("강의 제목은 필수입니다.");
        }

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("강의 내용은 필수입니다.");
        }

        sectionDAO.insertSection(con,villageId, chapNo, sectionName, content, videoUrl);
    }
    public List<SectionDTO> findSectionsByVillageId(Long villageId) {
        return sectionDAO.findSectionsByVillageId(con, villageId);
    }
}