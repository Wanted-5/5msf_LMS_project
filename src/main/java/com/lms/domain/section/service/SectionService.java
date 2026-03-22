package com.lms.domain.section.service;

import com.lms.domain.section.dao.SectionDAO;
import com.lms.domain.section.dto.SectionDTO;
import com.lms.domain.section.dto.response.SectionListResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionService {

    private final Connection con;
    private final SectionDAO sectionDAO;

    public SectionService(Connection con) {
        this.con = con;
        this.sectionDAO = new SectionDAO(con);
    }

    public List<SectionListResponse> findSectionsByVillageId(long villageId) {
        try {
            List<SectionDTO> sectionDTOList = sectionDAO.findSectionsByVillageId(villageId);

            List<SectionListResponse> responseList = new ArrayList<>();

            for (SectionDTO sectionDTO : sectionDTOList) {
                SectionListResponse response = new SectionListResponse(
                        sectionDTO.getSectionId(),
                        sectionDTO.getChapNo(),
                        sectionDTO.getSectionName(),
                        sectionDTO.getStatus()
                );

                responseList.add(response);
            }

            return responseList;

        } catch (SQLException e) {
            throw new RuntimeException("[DB error] 강의 목록 조회에 실패했습니다.", e);
        }
    }

    public SectionDTO findSectionById(long sectionId) {
        return sectionDAO.findSectionById(con, sectionId);
    }

    public SectionDTO findSectionByVillageIdAndSectionId(long villageId, long sectionId) {
        return sectionDAO.findSectionByVillageIdAndSectionId(con, villageId, sectionId);
    }

}