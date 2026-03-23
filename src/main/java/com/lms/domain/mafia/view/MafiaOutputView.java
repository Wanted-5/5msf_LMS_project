package com.lms.domain.mafia.view;

public class MafiaOutputView {


    public void printTodayMafia(Long userId) {
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("      🩸 오늘의 마피아가 선정되었습니다... 🩸  ");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("  🔪 게시판을 확인하세요...                    ");
        System.out.println("  💀 오늘의 마피아 : " + userId + "            ");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
    }

    public void printError(String message) {
        System.out.println("알림 : " + message);
    }


}
