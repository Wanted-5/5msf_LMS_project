package com.lms.domain.village.service;

import com.lms.domain.city.dao.CityDAO;
import com.lms.domain.city.dto.CityDTO;
import com.lms.domain.village.dao.VillageDAO;
import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.domain.village.dto.response.CreateVillageResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class VillageService {

    private final VillageDAO villageDAO;
    private final Connection con;

    public VillageService(Connection con) {
        this.con = con;
        this.villageDAO = new VillageDAO(con);
    }

    public VillageDTO enterVillageByInviteCode(String inviteCode) {
        VillageDTO village = villageDAO.findVillageByInviteCode(con, inviteCode);

        if (village == null) {
            throw new IllegalArgumentException("초대코드에 해당하는 마을이 없습니다.");
        }


        boolean validPeriod = villageDAO.isVillagePeriodValid(con, village.getVillageId());
        if (!validPeriod) {
            throw new IllegalStateException("현재 마을 입장 가능 기간이 아닙니다.");
        }

        boolean approved = villageDAO.isStudentApproved(con, village.getVillageId());
        if (!approved) {
            throw new IllegalStateException("아직 승인 대기 상태입니다.");
        }

        return village;
    }

    public CreateVillageResponse createVillage(CreateVillageRequest request) {

        CityDAO cityDAO = new CityDAO(con);
        try {
            CityDTO existingCity = cityDAO.findById(request.getCityId());

            if (existingCity == null) {
                throw new IllegalArgumentException("도시가 존재하지 않습니다.");
            }

            if (request.getVillageName() == null || request.getVillageName().trim().isEmpty()) {
                throw new IllegalArgumentException("마을 이름은 필수입니다. 공허한 마을는 건설할 수 없습니다.");
            }

            if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
                throw new IllegalArgumentException("마을 설명은 필수입니다. 해당 마을 설명을 입력해주세요.");
            }

            if (request.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("시작일은 오늘 이후여야 합니다.");
            }

            if (request.getEndDate() != null && request.getStartDate() != null) {
                if (request.getEndDate().isBefore(request.getStartDate())) {
                    throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
                }
            }

            if (!request.getEndDate().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("종료은 오늘 이후여야 합니다.");
            }

            Long newVillageId = villageDAO.createVillage(request);

            return new CreateVillageResponse(
                    newVillageId,
                    request.getVillageName(),
                    request.getInviteCode()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
