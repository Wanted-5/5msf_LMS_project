package com.lms.domain.attendance.service;

import com.lms.domain.attendance.dao.AttendDAO;
import com.lms.domain.attendance.dto.AttendDTO;
import com.lms.domain.attendance.dto.AttendStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendService {
    private final AttendDAO attendDAO;
    private final Connection connection;

    public AttendService(Connection connection) {
        this.attendDAO = new AttendDAO(connection);
        this.connection = connection;
    }


    public boolean checkAttendance(AttendDTO attendDTO) {
        try {
            LocalTime now = LocalTime.now();
            LocalTime late = LocalTime.of(9, 30);
            LocalTime absent = LocalTime.of(10, 0);

            if (!now.isAfter(late)) {
                attendDTO.setStatus(AttendStatus.PRESENT);
            } else if (!now.isAfter(absent)) {
                attendDTO.setStatus(AttendStatus.LATE);
            } else {
                attendDTO.setStatus(AttendStatus.ABSENT);
            }

            int result = attendDAO.attendInsert(attendDTO);

            if (result > 0) {
                System.out.println("🎉 [" + attendDTO.getStatus().getDescription() + "] 처리가 완료되었습니다. (현재시간: " + now + ")");
                return true;
            }

            return false;

        } catch (SQLException e) {
            // MySQL 에러 코드 1062는 Unique 제약 조건 위반
            if (e.getErrorCode() == 1062) {
                System.out.println("⚠️ [안내] 오늘은 이미 출석 체크를 완료하셨습니다.");
            } else {
                System.out.println("🚨 출석 체크 중 DB 오류 발생: " + e.getMessage());
            }
            return false;
        }
    }

    public List<AttendDTO> getMyAttendance(long userId) {
        try {
            return attendDAO.findAttendAll(userId);
        } catch (SQLException e) {
            System.out.println("출결 조회 중 DB 오류: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public List<AttendDTO> getAllAttendanceForAdmin() {
        try {
            return attendDAO.findAttendAllForAdmin();
        } catch (SQLException e) {
            System.out.println("전체 출결 조회 중 DB 오류: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public List<AttendDTO> getAttendanceByDate(LocalDate targetDate) {
        try {
            return attendDAO.findAttendByDate(targetDate);
        } catch (SQLException e) {
            System.out.println("날짜별 출결 조회 중 DB 오류 발생: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public boolean updateAttendStatus(long attendanceId, AttendStatus status) {
        try {
            int result = attendDAO.updateAttendStatus(attendanceId, status);

            return result > 0;

        } catch (SQLException e) {
            System.out.println("출결 상태 수정 중 DB 오류: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteAttendance(long attendanceId) {
        try {

            int result = attendDAO.deleteAttendance(attendanceId);

            return result > 0;

        } catch (SQLException e) {
            System.out.println("출결 기록 삭제 중 DB 오류 발생: " + e.getMessage());
            return false;
        }
    }
}
