package com.lms.domain.section.model.service;

import com.lms.domain.section.model.DAO.SectionDAO;
import com.lms.domain.section.model.DTO.SectionDTO;

import java.sql.Connection;
import java.util.List;

public class SectionService {

    private final Connection con;
    private final SectionDAO sectionDAO;

    public SectionService(Connection con) {
        this.con = con;
        this.sectionDAO = new SectionDAO();
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
}