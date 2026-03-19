package com.lms.domain.village.view;

import com.lms.domain.village.model.DTO.VillageDTO;

public class VillageOutputView {

    public void printError(String message) {
        System.out.println("[오류] " + message);
    }

    public void printVillageEnterSuccess(VillageDTO village) {
        System.out.println("\n마을 입장 성공");
        System.out.println("마을명: " + village.getVillageName());
    }
}