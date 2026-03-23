package com.lms.domain.enrollment.dto.Response;

import com.lms.domain.enrollment.dto.EnrollmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnterVillageResponse {
    private Long villageId;
    private String villageName;
    private EnrollmentStatus status;
    private LocalDateTime appliedAt;

    public EnterVillageResponse(Long villageId, String villageName, EnrollmentStatus status, LocalDateTime appliedAt) {
        this.villageId = villageId;
        this.villageName = villageName;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getVillageId() {
        return villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}
