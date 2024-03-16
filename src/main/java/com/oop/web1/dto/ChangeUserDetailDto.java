package com.oop.web1.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ChangeUserDetailDto {
    private String userName;
    private String password;
    private String fullName;
    private String avatar;
    private LocalDate dateOfBirth;
}
