package com.lms.domain.village.model.DTO;

public class VillageDTO {

    private long villageId;
    private String villageName;



    public enum Role {
        ADMIN, INSTRUCTOR, STUDENT
    }

    public VillageDTO() {
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