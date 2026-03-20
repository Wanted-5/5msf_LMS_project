package com.lms.domain.attendance.controller;

import com.lms.domain.attendance.service.AttendService;

public class AttendController {
    private final AttendService attendService;

    public AttendController(AttendService attendService) {
        this.attendService = attendService;
    }
}
