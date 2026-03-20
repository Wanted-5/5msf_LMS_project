//package com.lms.domain.attendance.controller;
//
//

//
//public class AttendController {
//
//    private final AttendService attendService;
//
//
//    public AttendController(AttendService attendService) {
//        this.attendService = attendService;
//    }
//
//    public attendStatus attendCheck() {
//        String loggedInEmpId = UserSession.getLoggedInUserId();
//
//        // Service를 호출하고, 그 결과(Enum)를 그대로 View(호출한 쪽)로 다시 던져줍니다.
//        return service.processAttendance(loggedInEmpId);
//    }
//}
