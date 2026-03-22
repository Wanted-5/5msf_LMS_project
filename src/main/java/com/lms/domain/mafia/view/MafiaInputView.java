package com.lms.domain.mafia.view;

import com.lms.domain.mafia.controller.MafiaController;
import com.lms.domain.mafia.dto.MafiaDTO;

import com.lms.domain.users.dto.UserRole;
import com.lms.global.common.UserSession;

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
        // 강사 권한 체크 로직 담겨있음 나중에 강사에다가 넘겨주기
        selectMafia(1);
        mafiaController.selectVillageAll();
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
