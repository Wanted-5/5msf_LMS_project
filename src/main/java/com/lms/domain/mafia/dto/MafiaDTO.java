package com.lms.domain.mafia.dto;

import java.time.LocalDate;

public class MafiaDTO {

    private int stayId;
    private int villageId;
    private int userId;
    private int mafiaId;
    private String status;
    private LocalDate appliedAt;
    private LocalDate createdAt;

    public MafiaDTO() {}

    public MafiaDTO(int stayId, int villageId, int userId, String status, LocalDate appliedAt) {
        this.stayId = stayId;
        this.villageId = villageId;
        this.userId = userId;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public MafiaDTO(int userId, int villageId, LocalDate createdAt) {
        this.userId = userId;
        this.villageId = villageId;
        this.createdAt = createdAt;
    }

    public MafiaDTO(int userId, String status, LocalDate appliedAt) {
        this.userId = userId;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public MafiaDTO(int mafiaId, int userId, int villageId, LocalDate createdAt) {
        this.mafiaId = mafiaId;
        this.userId = userId;
        this.villageId = villageId;
        this.createdAt = createdAt;
    }

    public int getStayId() {
        return stayId;
    }

    public void setStayId(int stayId) {
        this.stayId = stayId;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMafiaId() {
        return mafiaId;
    }

    public void setMafiaId(int mafiaId) {
        this.mafiaId = mafiaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDate appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }



    public void setSelectedDate(LocalDate now) {

    }
}
