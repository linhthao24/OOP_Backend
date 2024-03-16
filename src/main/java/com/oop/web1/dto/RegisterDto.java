package com.oop.web1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    String userName;
    String email;
    String password;
    String fullName;
    boolean gender;
    String phoneNumber;
    String dateOfBirth;
    private String Avatar;
    public String getAvatar() {
        this.Avatar = "insert data after login";
        return Avatar;
    }
    public String setAvatar(String Avatar) {
        this.Avatar = "insert data after login";
        return Avatar;
    }
}
