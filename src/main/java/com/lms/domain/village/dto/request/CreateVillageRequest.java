package com.lms.domain.village.dto.request;

import java.time.LocalDate;

public class CreateVillageRequest {
    private Long cityId;
    private String villageName;
    private String description;
    private String inviteCode;
    private LocalDate startDate;
    private LocalDate endDate;

    public CreateVillageRequest(Long cityId, String villageName, String description, String inviteCode,
                                LocalDate startDate, LocalDate endDate) {
        this.cityId = cityId;
        this.villageName = villageName;
        this.description = description;
        this.inviteCode = inviteCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getVillageName() {
        return villageName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
