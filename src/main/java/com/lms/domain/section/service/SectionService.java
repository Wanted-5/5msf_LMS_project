package com.lms.domain.section.service;

import com.lms.domain.section.dao.SectionDAO;
import com.lms.domain.section.dto.SectionDTO;
import com.lms.domain.section.dto.request.SectionDetailRequest;
import com.lms.domain.section.dto.response.SectionDetailResponse;
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

    public SectionDetailResponse findSectionBySectionId(SectionDetailRequest request) {

        if (request.getSectionId() == null || request.getSectionId() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 강의 번호입니다.");
        }

        try {
            SectionDTO detailSection = sectionDAO.findSectionBySectionId(request);

            if (detailSection == null) {
                throw new IllegalArgumentException("존재하지 않거나 삭제된 강의입니다.");
            }

            if (detailSection.getStatus().equals("HIDDEN")) {
                throw new IllegalArgumentException("현재 강사님에 의해 비공개 처리된 강의입니다.");
            }

            if (request.getVillageId() != null && request.getVillageId() != detailSection.getVillageId()) {
                throw new IllegalArgumentException("해당 마을의 수강생만 접근할 수 있는 강의입니다.");
            }
            return new SectionDetailResponse(
                    detailSection.getSectionId(),
                    detailSection.getUserId(),
                    detailSection.getChapNo(),
                    detailSection.getSectionName(),
                    detailSection.getContent(),
                    detailSection.getVideoUrl()
            );

        } catch (SQLException e) {
            throw new RuntimeException("[DB error] 강의 상세 조회 중 시스템 오류가 발생했습니다.", e);
        }

    }
}