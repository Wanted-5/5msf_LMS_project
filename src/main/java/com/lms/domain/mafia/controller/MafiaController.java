package com.lms.domain.mafia.controller;

import com.lms.domain.mafia.service.MafiaService;

public class MafiaController {

    private final MafiaService mafiaService;

    public MafiaController(MafiaService mafiaService) {
        this.mafiaService = mafiaService;
    }
}
