package com.lms.domain.users.dto.response;

public class MyPageUpdateResponse {
    private String updatedValue;
    private String message;

    public MyPageUpdateResponse(String updatedValue, String message) {
        this.updatedValue = updatedValue;
        this.message = message;
    }

    public String getUpdatedValue() {
        return updatedValue;
    }

    public String getMessage() {
        return message;
    }
}
