package com.lms.domain.users.dto;

import com.lms.domain.users.constant.UserRole;

import java.time.LocalDateTime;

public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String address;
    private boolean gender;
    private String introduction;
    private String profileImgUrl;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO() {
    }

    public UserDTO(Long userId, String username, String password, String email,
                   String name, String nickname, String phoneNumber, String address,
                   boolean gender, String introduction, String profileImgUrl, UserRole role,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.introduction = introduction;
        this.profileImgUrl = profileImgUrl;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User 정보 조회 {" +
                "userId=" + userId +
                ", 아이디: '" + username + '\'' +
                ", 비밀번호:'" + password + '\'' +
                ", 이메일: '" + email + '\'' +
                ", 이름: '" + name + '\'' +
                ", 닉네임: '" + nickname + '\'' +
                ", 전화번호: '" + phoneNumber + '\'' +
                ", 주소: '" + address + '\'' +
                ", 성별: " + gender +
                ", 한줄소개: '" + introduction + '\'' +
                ", 프로필이미지: '" + profileImgUrl + '\'' +
                ", 권한: " + role +
                ", 생성일: " + createdAt +
                ", 수정일: " + updatedAt +
                '}';
    }
}
