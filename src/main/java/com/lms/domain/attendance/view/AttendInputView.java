package com.lms.domain.attendance.view;

import com.lms.domain.attendance.controller.AttendController;

import java.util.Scanner;

public class AttendInputView {
    private final AttendController attendController;
    private final AttendOutputView attendOutputView;
    Scanner sc = new Scanner(System.in);

    public AttendInputView(AttendController attendController, AttendOutputView attendOutputView) {
        this.attendController = attendController;
        this.attendOutputView = attendOutputView;
    }

    public void attenedStart(){



    }

}
