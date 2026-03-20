package com.lms.domain.village.model.service;

import com.lms.domain.village.model.DAO.VillageDAO;
import com.lms.domain.village.model.DTO.VillageDTO;

import java.sql.Connection;

public class VillageService {

    private final VillageDAO villageDAO;
    private final Connection con;

    public VillageService(Connection con) {
        this.con = con;
        this.villageDAO = new VillageDAO();
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
}