package com.lms.domain.village.dto.response;

public class CreateVillageResponse {
    private Long villageId;
    private String villageName;
    private String inviteCode;

    public CreateVillageResponse(Long villageId, String villageName, String inviteCode) {
        this.villageId = villageId;
        this.villageName = villageName;
        this.inviteCode = inviteCode;
    }

    public Long getVillageId() {
        return villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
