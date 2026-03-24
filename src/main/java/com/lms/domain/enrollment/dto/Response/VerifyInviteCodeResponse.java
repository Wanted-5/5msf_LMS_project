package com.lms.domain.enrollment.dto.Response;

import java.time.LocalDate;

// 입장 코드 일치 조회 후, 마을 정보 보여주는 Response
public class VerifyInviteCodeResponse {
    private final long villageId;
    private final String villageName;
    private final String description;
    private final LocalDate startDate;

    public VerifyInviteCodeResponse(long villageId, String villageName, String description, LocalDate startDate) {
        this.villageId = villageId;
        this.villageName = villageName;
        this.description = description;
        this.startDate = startDate;
    }

    public long getVillageId() {
        return villageId;
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
}
