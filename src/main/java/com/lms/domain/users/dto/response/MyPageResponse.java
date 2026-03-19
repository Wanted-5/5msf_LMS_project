package com.lms.domain.users.dto.response;

public class MyPageResponse {
    private String name;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String introduction;
    //Todo 소속 마을은 추후 추가 예정


    public MyPageResponse(String name, String nickname, String email,
                          String phoneNumber, String introduction) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIntroduction() {
        return introduction;
    }
}
