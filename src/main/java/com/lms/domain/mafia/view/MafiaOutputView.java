package com.lms.domain.mafia.view;

public class MafiaOutputView {


    public void printTodayMafia(Long userId) {
        System.out.println("오늘의 마피아가 선정되었습니다! 게시판을 확인하세요");
        System.out.println("오늘의 마피아입니다 : " + userId);
    }

    public void printError(String message) {
        System.out.println("알림 : " + message);
    }


}
