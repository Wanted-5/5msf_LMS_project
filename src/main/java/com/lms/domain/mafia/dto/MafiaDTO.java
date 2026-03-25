package com.lms.domain.mafia.dto;

import java.time.LocalDate;

public class MafiaDTO {

    private Long enrollmentId;
    private Long villageId;
    private Long userId;
    private Long mafiaId;
    private String status;
    private LocalDate appliedAt;
    private LocalDate createdAt;
    private String name;
    private String nickname;

    public MafiaDTO(Long enrollmentId, Long villageId, Long userId, Long mafiaId, String status, LocalDate appliedAt, LocalDate createdAt, String name, String nickname) {
        this.enrollmentId = enrollmentId;
        this.villageId = villageId;
        this.userId = userId;
        this.mafiaId = mafiaId;
        this.status = status;
        this.appliedAt = appliedAt;
        this.createdAt = createdAt;
        this.name = name;
        this.nickname = nickname;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public MafiaDTO() {
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMafiaId() {
        return mafiaId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getAppliedAt() {
        return appliedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMafiaId(long mafiaId) {
        this.mafiaId = mafiaId;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
