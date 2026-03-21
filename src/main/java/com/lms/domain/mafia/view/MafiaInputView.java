package com.lms.domain.mafia.view;

import com.lms.domain.mafia.controller.MafiaController;
import com.lms.domain.mafia.dto.MafiaDTO;

import java.util.Scanner;

public class MafiaInputView {

    private final MafiaController mafiaController;
    private final MafiaOutputView mafiaOutputView;
    Scanner sc = new Scanner(System.in);


    public MafiaInputView(MafiaController mafiaController, MafiaOutputView mafiaOutputView) {
        this.mafiaController = mafiaController;
        this.mafiaOutputView = mafiaOutputView;
    }

    // 테스트 하기 위해 생성
    public void mafiaTest() {
        mafiaController.selectVillageAll();
        //selectMafia(1);
    }

    private MafiaDTO selectMafia(int villageId) {
        try {
            MafiaDTO todayMafia = mafiaController.selectMafia(villageId);
            mafiaOutputView.printTodayMafia(todayMafia.getUserId());
        } catch (RuntimeException e) {
            mafiaOutputView.printError(e.getMessage());
        }
        return null;
    }

    private int inputInt() {

        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요 : ");
            }
        }

    }

}
