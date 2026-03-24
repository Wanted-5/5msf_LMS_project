package com.lms.domain.mafia.view;

import com.lms.domain.mafia.controller.MafiaController;
import com.lms.domain.mafia.dto.MafiaDTO;

import com.lms.domain.mafia.dto.Response.SelectMafiaResponse;
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

    public void selectMafia(long villageId) {

        // TODO : 강사 쪽으로 기능 넘겨주면 됨
        // 오늘의 마피아 뽑는 메소드에 강사만 뽑을 수 있게 권한 부여
        if(UserSession.getLoggedInUser().getRole() != UserRole.INSTRUCTOR) {
            throw new RuntimeException("강사만 마피아를 뽑을 수 있습니다");
        }

        try {
            SelectMafiaResponse response = mafiaController.selectMafia(villageId);
            mafiaOutputView.printTodayMafia(response);
        } catch (RuntimeException e) {
            mafiaOutputView.printError(e.getMessage());
        }
    }

    public void selectTodayMafiaInfo(long villageId) {
        try {
            UserRole role = UserSession.getLoggedInUser().getRole();
            Long userId = UserSession.getLoggedInUser().getUserId();

            SelectMafiaResponse response = mafiaController.selectTodayMafiaInfo(villageId);
            // 권한 체크
            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                mafiaOutputView.printTodayMafia(response);
            } else if (response.getUserId() == userId.intValue()) {
                mafiaOutputView.printTodayMafia(response);
            } else {
                System.out.println("  🚨 오늘의 마피아만 확인할 수 있습니다.");
            }
        } catch (RuntimeException e) {
            System.out.println("  🚨 " + e.getMessage());
        }
    }

    //
    public void selectVillageAll() {
        mafiaController.selectVillageAll();
    }


}
