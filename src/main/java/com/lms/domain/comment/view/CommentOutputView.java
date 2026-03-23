package com.lms.domain.comment.view;

import com.lms.domain.comment.dto.CommentDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentOutputView {

    public void printSuccess(String message) {
        System.out.println("✅ " + message);
    }

    public void printError(String message) {
        System.out.println("🚨🚨 " + message);
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printComment(List<CommentDTO> commentList) {

        if (commentList == null || commentList.isEmpty()) {
            System.out.println("\n 📭 작성된 댓글이 없습니다.");
            return;
        }

        // 🌟 날짜를 예쁘게 출력하기 위한 포매터 설정
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

        System.out.println("\n==========================================================================");
        System.out.println("                         💬 댓글 목록");
        System.out.println("==========================================================================");
        System.out.printf("%-5s | %-10s | %-15s | %-20s\n", "번호", "작성자(ID)", "작성/수정 시간", "내용");
        System.out.println("--------------------------------------------------------------------------");

        // 🌟 순번을 매기기 위해 for문 변경 (인덱스 사용)
        for (int i = 0; i < commentList.size(); i++) {
            CommentDTO comment = commentList.get(i);

            // 수정된 댓글이면 updated_at을, 아니면 created_at을 출력하도록 처리 (선택 사항)
            String timeStr = (comment.getUpdatedAt() != null)
                    ? comment.getUpdatedAt().format(timeFormatter)
                    : comment.getCreatedAt().format(timeFormatter);

            // 💡 [참고] 현재 DTO에 작성자 이름이 없어서 creatorId를 출력합니다.
            // 내용이 너무 길면 잘리니까 20자까지만 보여주도록 처리 (콘솔창 깨짐 방지)
            String shortContent = comment.getContent();
            if (shortContent.length() > 20) {
                shortContent = shortContent.substring(0, 20) + "...";
            }

            // 🌟 [핵심] 리스트 순번(i+1), 작성자 ID, 시간, 내용 출력
            System.out.printf("[ %2d ] | %-10d | %-15s | %s\n",
                    (i + 1),
                    comment.getCreatorId(),
                    timeStr,
                    shortContent);
        }
        System.out.println("==========================================================================\n");
    }
}