package com.lms.domain.attendance.dto;

import java.time.LocalDateTime;

public class AttendDTO {

    private Long attendanceId;
    private Long villageId;
    private Long userId;
    private AttendStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime attendanceDate;

    public AttendDTO(){
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AttendStatus getStatus() {
        return status;
    }

    public void setStatus(AttendStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateTime attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public AttendDTO(Long attendanceId, Long villageId, Long userId, AttendStatus status, LocalDateTime createdAt, LocalDateTime attendanceDate) {
        this.attendanceId = attendanceId;
        this.villageId = villageId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.attendanceDate = attendanceDate;
    }

    @Override
    public String toString() {
        return "AttendDTO{" +
                "attendanceId=" + attendanceId +
                ", villageId=" + villageId +
                ", userId=" + userId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", attendanceDate=" + attendanceDate +
                '}';
    }
}
