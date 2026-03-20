//package com.lms.domain.attendance.service;
//
//

//

//
//public class AttendService {
//
//    private final AttendDAO attendDAO;
//    private final Connection connection;
//
//
//    public AttendService(Connection connection) {
//        this.attendDAO = new AttendDAO(connection);
//        this.connection = connection;
//    }
//
//    public AttendDTO.attendStatus processAttendance(String user_id) {
//
//        boolean alreadyChecked = attendDAO.checkTodayAttendance(user_id);
//        if (alreadyChecked) {
//            return AttendDTO.attendStatus.ALREADY_CHECKED;
//        }
//
//        return AttendDTO.attendStatus.SUCCESS; // true 대신 Enum 상수 리턴!
//    }
//}
