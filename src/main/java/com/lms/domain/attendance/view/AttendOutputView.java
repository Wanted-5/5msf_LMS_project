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
        System.out.println("\n==========================================================================");
        System.out.println("                 📊 출결 현황 리스트              ");
        System.out.println("==========================================================================");

        if (list == null || list.isEmpty()) {
            System.out.println(" 📭 아직 기록된 출결 내역이 없습니다.");
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        System.out.printf("%-5s | %-10s | %-10s | %-15s\n", "번호", "이름", "상태", "처리 시간");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < list.size(); i++) {
            AttendDTO attend = list.get(i);
            System.out.printf("[%2d]  | %-10s | %-5s      | %-15s\n",
                    (i + 1),
                    attend.getUserName(),
                    attend.getStatus().getDescription(),
                    attend.getCreatedAt().format(timeFormatter));
        }
        System.out.println("==========================================================================\n");
    }
}
