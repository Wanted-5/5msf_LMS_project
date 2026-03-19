package com.lms.domain.village.controller;

import com.lms.domain.village.model.DTO.VillageDTO;
import com.lms.domain.village.model.service.VillageService;

public class VillageController {

    private final VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }

    public VillageDTO enterVillage(String inviteCode) {
        try {
            return villageService.enterVillageByInviteCode(inviteCode);
        } catch (Exception e) {
            System.out.println("마을 입장 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
}