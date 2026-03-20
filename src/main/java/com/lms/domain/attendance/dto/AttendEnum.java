package com.lms.domain.attendance.dto;

public class AttendEnum {

    public enum status {
        PRESENT("출석"),
        ABSENT("결석"),
        LATE("지각"),
        EARLY_LEAVE("조퇴"),
        EXCUSED("공결");

        private final String description;

        status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}




