package com.lms.domain.mafia.controller;

import com.lms.domain.mafia.dto.MafiaDTO;
import com.lms.domain.mafia.dto.Response.SelectMafiaResponse;
import com.lms.domain.mafia.service.MafiaService;

public class MafiaController {

    private final MafiaService mafiaService;

    public MafiaController(MafiaService mafiaService) {
        this.mafiaService = mafiaService;
    }

    // 마피아 뽑기
    public SelectMafiaResponse selectMafia(long villageId) {


        MafiaDTO todayMafia =  mafiaService.selectMafia(villageId);

        SelectMafiaResponse response = new SelectMafiaResponse(
                todayMafia.getName(),
                todayMafia.getNickname()
        );

        return response;
    }

     //마을 돌면서 마피아 뽑기
    public void selectVillageAll() {
        mafiaService.selectVillageAll();
    }

}
