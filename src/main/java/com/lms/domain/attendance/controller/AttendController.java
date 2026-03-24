package com.lms.domain.attendance.controller;

import com.lms.domain.attendance.dto.AttendDTO;
import com.lms.domain.attendance.dto.AttendStatus;
import com.lms.domain.attendance.service.AttendService;

import java.time.LocalDate;
import java.util.List;

public class AttendController {
    private final AttendService attendService;

    public AttendController(AttendService attendService) {
        this.attendService = attendService;
    }

    public void processAttendanceCheck(AttendDTO dto) {
        // 서비스에게 비즈니스 로직(시간 판별 + DB 저장)을 맡깁니다.
        boolean isSuccess = attendService.checkAttendance(dto);

        if (isSuccess) {
            System.out.println("출석 체크가 완료되었습니다!");
        } else {
            System.out.println("출석 체크에 실패했습니다. 관리자에게 문의하세요.");
        }
    }


    public List<AttendDTO> viewMyAttendance(long userId) {

        return attendService.getMyAttendance(userId);
    }
    public List<AttendDTO> viewAllAttendance() {
        return attendService.getAllAttendanceForAdmin();
    }
    public List<AttendDTO> viewAttendanceByDate(LocalDate targetDate) {
        return attendService.getAttendanceByDate(targetDate);
    }
    //discription5는 enum의 5가지 discription
    public boolean updateAttendStatus(long targetId, String description5) {

        AttendStatus targetStatus = AttendStatus.fromDescription(description5);

        if (targetStatus == null) {
            System.out.println("존재하지 않는 출결 상태입니다. (오타 확인)");
            return false;
        }

        return attendService.updateAttendStatus(targetId, targetStatus);
    }
    public boolean deleteAttendance(long targetId) {

        return attendService.deleteAttendance(targetId);
    }
}

