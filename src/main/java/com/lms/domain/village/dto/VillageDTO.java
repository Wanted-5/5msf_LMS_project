package com.lms.domain.village.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VillageDTO {

    private Long villageId;
    private Long cityId;
    private String villageName;
    private String description;
    private String inviteCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public VillageDTO(Long villageId, Long cityId, String villageName,
                      String description, String inviteCode, LocalDate startDate,
                      LocalDate endDate, String status, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
        this.villageId = villageId;
        this.cityId = cityId;
        this.villageName = villageName;
        this.description = description;
        this.inviteCode = inviteCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public VillageDTO() {
    }

    public Long getCityId() {
        return cityId;
    }

    public String getDescription() {
        return description;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public long getVillageId() {
        return villageId;
    }

    public void setVillageId(long villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}