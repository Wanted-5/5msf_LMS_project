package com.lms.domain.mafia.view;

import com.lms.domain.mafia.controller.MafiaController;
import com.lms.domain.mafia.dto.MafiaDTO;

import com.lms.domain.users.dto.UserRole;
import com.lms.global.common.UserSession;

import java.util.Scanner;

public class MafiaInputView {

    private final MafiaController mafiaController;
    private final MafiaOutputView mafiaOutputView;


    public MafiaInputView(MafiaController mafiaController, MafiaOutputView mafiaOutputView) {
        this.mafiaController = mafiaController;
        this.mafiaOutputView = mafiaOutputView;
    }

    public MafiaDTO selectMafia(int villageId) {

        // TODO : 강사 쪽으로 기능 넘겨주면 됨
        // 오늘의 마피아 뽑는 메소드에 강사만 뽑을 수 있게 권한 부여
        if(UserSession.getLoggedInUser().getRole() != UserRole.INSTRUCTOR) {
            throw new RuntimeException("강사만 마피아를 뽑을 수 있습니다");
        }

        try {
            MafiaDTO todayMafia = mafiaController.selectMafia(villageId);
            mafiaOutputView.printTodayMafia(todayMafia.getUserId());
        } catch (RuntimeException e) {
            mafiaOutputView.printError(e.getMessage());
        }
        return null;
    }

    public void selectVillageAll() {
        mafiaController.selectVillageAll();
    }


}
