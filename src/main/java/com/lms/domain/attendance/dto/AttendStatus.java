package com.lms.domain.attendance.dto;

public enum AttendStatus {
    PRESENT("출석"),
    ABSENT("결석"),
    LATE("지각"),
    EARLY_LEAVE("조퇴"),
    EXCUSED("공결");

    private final String description;

    AttendStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public static AttendStatus fromDescription(String discription5) {
        //이거 values() 안의 배열 하나씩 for문으로 출력
        for (AttendStatus status : AttendStatus.values()) {
            if (status.getDescription().equals(discription5)) {
                return status;
            }
        }
        return null;
    }
}