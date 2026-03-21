package com.lms.domain.attendance.view;

import com.lms.domain.attendance.dto.AttendDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendOutputView {

    public void printSuccess(String message) {

        System.out.println("✅ " + message);
    }

    public void printError(String message) {

        System.out.println("🚨🚨 " + message);
    }

    public void printAttendanceList(List<AttendDTO> list) {
        System.out.println("\n=================================================");
        System.out.println("                 📊 내 출결 현황");
        System.out.println("=================================================");

        if (list == null || list.isEmpty()) {
            System.out.println(" 📭 아직 기록된 출결 내역이 없습니다.");
            System.out.println("=================================================\n");
            return;
        }

        //형변환
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //간격 맞추기
        System.out.printf("%-15s | %-10s | %-15s\n", "출석 일자", "상태", "처리 시간");
        System.out.println("-------------------------------------------------");

        // 리스트를 돌면서 한 줄씩 예쁘게 출력
        for (AttendDTO attend : list) {

            // 1. Enum에서 "출석", "지각" 같은 예쁜 한글 설명을 꺼내옵니다.
            String statusKor = attend.getStatus().getDescription();

            // 2. 시간 객체를 예쁜 문자열로 포맷팅합니다.
            String dateStr = attend.getAttendanceDate().format(dateFormatter);
            String timeStr = attend.getCreatedAt().format(timeFormatter);

            // 3. printf로 줄을 딱딱 맞춰서 출력합니다.
            System.out.printf("%-15s | %-5s      | %-15s\n", dateStr, statusKor, timeStr);
        }
    }
}
