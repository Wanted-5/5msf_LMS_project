package com.lms.domain.mafia.view;

import com.lms.domain.mafia.dto.Response.SelectMafiaResponse;

public class MafiaOutputView {


    public void printTodayMafia(SelectMafiaResponse response) {
        // 이름과 닉네임 조합 (예: 홍길동 [의적])

        System.out.println("\n  ☠️ ════════════════════════════════════════════════════ ☠️");
        System.out.println("             🩸 오늘의 마피아가 선정되었습니다... 🩸        ");
        System.out.println("  ☠️ ════════════════════════════════════════════════════ ☠️");
        System.out.println("    🔪 간밤에 마을 어딘가에 수상한 코드가 남겨졌습니다...   ");
        System.out.println();
        System.out.println("       💀 지목된 마피아 : " + response.getRealname() + " [" + response.getNickname() + "]");
        System.out.println();
        System.out.println("    게시판을 확인하고 퀴즈를 풀어 마피아의 정체를 밝혀내세요! ");
        System.out.println("  ☠️ ════════════════════════════════════════════════════ ☠️\n");
    }

    public void printError(String message) {
        System.out.println("알림 : " + message);
    }


}
