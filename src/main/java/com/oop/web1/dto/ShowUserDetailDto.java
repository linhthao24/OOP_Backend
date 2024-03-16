package com.oop.web1.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ShowUserDetailDto {
    String userName;
    String email;
    String password;
    String fullName;
    boolean gender;
    String phoneNumber;
    LocalDate dateOfBirth;
    String Avatar;
}
