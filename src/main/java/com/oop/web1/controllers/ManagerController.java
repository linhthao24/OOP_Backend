package com.oop.web1.controllers;

import com.google.common.hash.Hashing;
import com.oop.web1.configs.AuthLead;
import com.oop.web1.configs.Role;
import com.oop.web1.dto.ChangeUserDetailDto;
import com.oop.web1.dto.LoginDto;
import com.oop.web1.dto.RegisterDto;
import com.oop.web1.dto.ShowUserDetailDto;
import com.oop.web1.entitys.AuthenticationEntity;
import com.oop.web1.routes.Routes;
import com.oop.web1.dto.managerDto.ListUserDto;
import com.oop.web1.entitys.UsersEntity;
import com.oop.web1.repositories.AuthenticationRepository;
import com.oop.web1.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// use case 1 + 2
@RestController
@RequestMapping(value = Routes.BASE_URL + Routes.MANAGER)
public class ManagerController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    HttpStatus httpStatus;
    HashMap<String, Object> map;
    // lay thong tin tat ca nguoi dung
    @GetMapping(value = Routes.LISTUSER)
    public List<ListUserDto> listUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.substring("bearer ".length()).trim();
        System.out.println(token);
        AuthLead auth = new AuthLead(authenticationRepository, userRepository);
        ResponseEntity response = auth.authManager(token);
        // System.out.println(response);
        map = (HashMap<String, Object>) response.getBody();
        // System.out.println(response.getStatusCode().is2xxSuccessful());
        if ((int) map.get("status") == 0) {
            List<UsersEntity> customerUsers = userRepository.findAllByRole(Role.role_customer);
            List<ListUserDto> listUserDtoList = customerUsers.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return listUserDtoList;
        } else {
            return null;
        }
    }
    private ListUserDto convertToDto(UsersEntity usersEntity) {
        ListUserDto listUserDto = new ListUserDto();
        listUserDto.setUserId(usersEntity.getUserId());
        listUserDto.setUserName(usersEntity.getUserName());
        listUserDto.setEmail(usersEntity.getEmail());
        listUserDto.setPassword(usersEntity.getPassword());
        listUserDto.setFullName(usersEntity.getFullName());
        listUserDto.setPhoneNumber(usersEntity.getPhoneNumber());
        listUserDto.setRole(usersEntity.getRole());
        // Các trường khác
        return listUserDto;
    }
    // xoa customer
    @DeleteMapping(value = Routes.DELETEUSERS)
    public ResponseEntity<String> deleteUserByEmail(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody List<String> emails){
        String token = authorizationHeader.substring("bearer ".length()).trim();
        //System.out.println(token);
        AuthLead auth = new AuthLead(authenticationRepository, userRepository);
        ResponseEntity response = auth.authManager(token);
        // System.out.println(response);
        map = (HashMap<String, Object>) response.getBody();
        if ((int) map.get("status") == 0) {
            for (String email : emails) {
                Optional<UsersEntity> userOpt = userRepository.findUsersEntityByEmail(email);
                if (userOpt.isPresent()) {
                    UsersEntity user = userOpt.get();
                    if(user.getRole() == Role.role_customer){
                        userRepository.delete(user);
                    }else{
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email isn't customer");
                    }
                }
            }
            return ResponseEntity.ok("Deleted users with provided emails.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
