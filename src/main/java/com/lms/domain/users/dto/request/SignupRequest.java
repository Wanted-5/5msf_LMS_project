package com.lms.domain.users.dto.request;

public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String address;
    private boolean gender;
    private String introduction;

    public SignupRequest(String username, String password, String email,
                         String name, String nickname, String phoneNumber,
                         String address, boolean gender, String introduction) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.introduction = introduction;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isGender() {
        return gender;
    }

    public String getIntroduction() {
        return introduction;
    }
}
