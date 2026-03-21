package com.lms.domain.village.controller;

import com.lms.domain.village.dto.VillageDTO;
import com.lms.domain.village.dto.request.CreateVillageRequest;
import com.lms.domain.village.dto.response.CreateVillageResponse;
import com.lms.domain.village.service.VillageService;

public class VillageController {

    private final VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }

    // TODO : 코드 사용하는지 확인하기.
    public VillageDTO enterVillage(String inviteCode) {
        try {
            return villageService.enterVillageByInviteCode(inviteCode);
        } catch (Exception e) {
            System.out.println("마을 입장 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    public CreateVillageResponse createVillageProcess(CreateVillageRequest request) throws Exception{
        return villageService.createVillage(request);
    }
}
