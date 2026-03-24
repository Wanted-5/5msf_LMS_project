package com.lms.domain.quizSubmission.view;

public class QuizSubOutputView {

    public void printCorrect() {
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("        ✅ 정답입니다! 살아남았습니다... ✅     ");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
    }

    public void printWrong() {
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
        System.out.println("        💀 오답입니다... 당신은 제거되었습니다 💀");
        System.out.println("  ☠️ ═══════════════════════════════════════ ☠️");
    }
    public void printError(String message) {
        System.out.println("🚨🚨" + message);
    }

}
