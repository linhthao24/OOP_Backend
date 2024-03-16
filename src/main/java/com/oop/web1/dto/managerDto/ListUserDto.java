package com.oop.web1.dto.managerDto;

import com.oop.web1.configs.Role;
import lombok.Getter;
import lombok.Setter;
import com.oop.web1.configs.Role;
@Getter
@Setter
public class ListUserDto {
    int userId;
    String userName;
    String email;
    String password;
    String fullName;
    String phoneNumber;
    Role role;
}
