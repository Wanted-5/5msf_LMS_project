package com.lms.domain.mafia.dto.Response;

public class SelectMafiaResponse {
    private String realname;
    private String nickname;
    private Long userId;

    public SelectMafiaResponse(String realname, String nickname) {
        this.realname = realname;
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getUserId() {
        return userId;
    }
}
