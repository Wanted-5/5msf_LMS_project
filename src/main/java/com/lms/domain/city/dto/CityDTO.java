package com.lms.domain.city.dto;

import com.lms.domain.users.constant.UserRole;
import com.lms.global.common.UserSession;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CityDTO {

    private Long cityId;
    private String cityName;
    private String description;
    private boolean isActive;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public CityDTO() {
    }

    public CityDTO(Long cityId, String cityName, String description, boolean isActive,
                   LocalDateTime created_at, LocalDateTime updated_at) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.description = description;
        this.isActive = isActive;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
