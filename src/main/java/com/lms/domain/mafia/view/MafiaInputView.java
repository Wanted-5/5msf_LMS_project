package com.lms.domain.mafia.view;

import com.lms.domain.mafia.controller.MafiaController;

import java.util.Scanner;

public class MafiaInputView {

    private final MafiaController mafiaController;
    private final MafiaOutputView mafiaOutputView;
    Scanner sc = new Scanner(System.in);


    public MafiaInputView(MafiaController mafiaController, MafiaOutputView mafiaOutputView) {
        this.mafiaController = mafiaController;
        this.mafiaOutputView = mafiaOutputView;
    }

}
