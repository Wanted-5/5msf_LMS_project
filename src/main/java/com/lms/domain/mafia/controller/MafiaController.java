package com.lms.domain.mafia.controller;

import com.lms.domain.mafia.dto.MafiaDTO;
import com.lms.domain.mafia.service.MafiaService;

public class MafiaController {

    private final MafiaService mafiaService;

    public MafiaController(MafiaService mafiaService) {
        this.mafiaService = mafiaService;
    }

    // 마피아 뽑기
    public MafiaDTO selectMafia(int villageId) {

        // TODO : 지금은 테스트라서 생성로직에 sout 했지만 추후 강상와 본인만 보이도록 조회로 이동
        return mafiaService.selectMafia(villageId);

    }

    // 마을 돌면서 마피아 뽑기
    public void selectVillageAll() {
        mafiaService.selectVillageAll();
    }



}
