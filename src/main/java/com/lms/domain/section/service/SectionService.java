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

    // 강사
    public void createSection(long villageId, long userId, int chapNo,
                              String sectionName, String content, String videoUrl) {

        if (chapNo <= 0) {
            throw new IllegalArgumentException("주차(chap_no)는 1 이상이어야 합니다.");
        }
        if (sectionName == null || sectionName.isBlank()) {
            throw new IllegalArgumentException("강의 제목은 필수입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("강의 내용은 필수입니다.");
        }

        try {
            int result = sectionDAO.insertSection(con, villageId, userId, chapNo, sectionName, content, videoUrl);

            if (result <= 0) {
                throw new RuntimeException("섹션 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("섹션 등록 중 DB 오류가 발생했습니다.", e);
        }
    }

}