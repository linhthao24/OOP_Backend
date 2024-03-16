package com.oop.web1.controllers;

import com.oop.web1.dto.LoginDto;
import com.oop.web1.routes.Routes;
import com.oop.web1.dto.RegisterDto;
import com.oop.web1.entitys.UsersEntity;
import com.oop.web1.repositories.UserRepository;
import com.oop.web1.configs.Role;
import com.oop.web1.entitys.AuthenticationEntity;
import com.oop.web1.repositories.AuthenticationRepository;
import com.oop.web1.configs.Auth;
import com.oop.web1.dto.ChangeUserDetailDto;
import com.oop.web1.dto.ShowUserDetailDto;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

// use case 1 + 2
@RestController
@RequestMapping(value = Routes.BASE_URL + Routes.USER )
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    HttpStatus httpStatus;
    HashMap<String, Object> map;

    private String genKey(int user_Id){
        double random = Math.random();
        String apiKey = Hashing.sha256()
                .hashString(random + "" + user_Id, StandardCharsets.UTF_8 )
                .toString();
        try{
            AuthenticationEntity authentication = new AuthenticationEntity();
            authentication.setUser_Id(user_Id); // Gán user_Id vào bản ghi mới trong AuthenticationEntity
            authentication.setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            authentication.setExpiredAt(new Date(System.currentTimeMillis() + 86400000 ));
            authentication.setApiKey(apiKey);
            authenticationRepository.save(authentication);
            return apiKey;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // C: dang ky
    @PostMapping(value = Routes.REGISTER)
    public ResponseEntity register(@RequestBody RegisterDto registerDto){
        map = new HashMap<>();
        UsersEntity existingUser = userRepository.findUsersEntityByEmail(registerDto.getEmail()).orElse(null);
        if( existingUser != null ){
            map.put("status", -5);
            map.put("error", "User already exists");
            httpStatus = HttpStatus.CONFLICT;
        }else{
            UsersEntity user = UsersEntity.builder()
                    .userName(registerDto.getUserName())
                    .email(registerDto.getEmail())
                    .password(registerDto.getPassword())
                    .fullName(registerDto.getFullName())
                    .gender(registerDto.isGender())
                    .phoneNumber(registerDto.getPhoneNumber())
                    .dateOfBirth(LocalDate.parse(registerDto.getDateOfBirth()))
                    .role(Role.role_customer)
                    .avatar(registerDto.getAvatar())
                    .build();
            userRepository.save(user);

            map.put("status", 1);
            map.put("message", "User registered successfully");
            httpStatus = HttpStatus.CREATED;
        }
        return ResponseEntity.status(httpStatus).body(map);
    }
    // dang nhap
    @PostMapping(value = Routes.LOGIN)
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        map = new HashMap<>();
        HttpStatus httpStatus1 = HttpStatus.OK;
        UsersEntity user = userRepository.findUsersEntityByEmail(loginDto.getEmail()).orElse(null);
        if(user != null){
            if (user.getPassword().equals(loginDto.getPassword())){
                map.put("status", 0);
                map.put("success", user.getEmail() + "has been logged in");
                map.put("Id",user.getUserId());
                map.put("name", user.getUserName());
                map.put("Role", user.getRole());
                map.put("apiKey", genKey(user.getUserId()));
                httpStatus1 = HttpStatus.OK;
            }else{
                map.put("status", -3);
                map.put("error", "Password entered is invalid");
                httpStatus1 = HttpStatus.UNAUTHORIZED;
            }
        }else{
            map.put("status", -3);
            map.put("error", "No such username found");
            httpStatus1 = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(map,httpStatus1);
    }
    // R: lay thong tin nguoi dung
    @GetMapping(value = Routes.USERDETAIL + "/{user_Id}")
    public ShowUserDetailDto showDetail(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable int user_Id
            ){
        String token = authorizationHeader.substring("bearer ".length()).trim();
        Auth auth = new Auth(authenticationRepository);
        ResponseEntity reponse = auth.authorzire(token);
        map = (HashMap<String, Object>) reponse.getBody();
        if((int) map.get("status") == 0){
            Optional<UsersEntity> userOptional = userRepository.findUsersEntityByUserId(user_Id);
            if(userOptional.isPresent()){
                UsersEntity user = userOptional.get();
                ShowUserDetailDto userDetailDto = new ShowUserDetailDto();
                userDetailDto.setUserName(user.getUserName());
                userDetailDto.setEmail(user.getEmail());
                userDetailDto.setPassword(user.getPassword());
                userDetailDto.setFullName(user.getFullName());
                userDetailDto.setGender(user.isGender());
                userDetailDto.setPhoneNumber(user.getPhoneNumber());
                userDetailDto.setDateOfBirth(user.getDateOfBirth());
                userDetailDto.setAvatar(user.getAvatar());
                return userDetailDto;
            }
        }
        return null;
    }
    // U: chinh sua thong tin
    @PostMapping(value = Routes.CHANGE + "/{user_Id}")
    public ResponseEntity change(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable int user_Id,
            @RequestBody (required = false)ChangeUserDetailDto changeUserDetailDto
            ){
        String token = authorizationHeader.substring("bearer ".length()).trim();
        Auth auth = new Auth(authenticationRepository);
        ResponseEntity reponse = auth.authorzire(token);
        map = (HashMap<String, Object>) reponse.getBody();
        if((int) map.get("status") == 0) {
            Optional<UsersEntity> userOptional = userRepository.findUsersEntityByUserId(user_Id);
            if (userOptional.isPresent()) {
                UsersEntity user = userOptional.get();
                user.setFullName(changeUserDetailDto.getFullName() != null ? changeUserDetailDto.getFullName() : user.getFullName());
                user.setPassword(changeUserDetailDto.getPassword() != null ? changeUserDetailDto.getPassword() : user.getPassword());
                user.setAvatar(changeUserDetailDto.getAvatar() != null ? changeUserDetailDto.getAvatar() : user.getAvatar());
                user.setUserName(changeUserDetailDto.getUserName() != null ? changeUserDetailDto.getUserName() : user.getUserName());
                user.setDateOfBirth(changeUserDetailDto.getDateOfBirth() != null ? changeUserDetailDto.getDateOfBirth() : user.getDateOfBirth());
                userRepository.save(user);

                map.put("status", 0);
                map.put("error", "changed success");
                httpStatus = HttpStatus.OK;
            } else {
                map.put("status", -4);
                map.put("error", "User not found");
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } else {
            map.put("status", -3);
            map.put("error", "can't auth");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(map, httpStatus);
    }
    // Logout
    @PostMapping(value = Routes.LOGOUT)
    public ResponseEntity logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String token = authorizationHeader.substring("bearer ".length()).trim();
        Auth auth = new Auth(authenticationRepository);
        ResponseEntity reponse = auth.authorzire(token);
        System.out.println(reponse);
        map = (HashMap<String, Object>) reponse.getBody();
        if((int) map.get("status") == 0){
            AuthenticationEntity authenticationEntity = authenticationRepository.findByApiKey(token);
            if(authenticationEntity != null){
                authenticationRepository.delete(authenticationEntity);
                map.put("status", 0);
                map.put("success", "You have been logged out");
                return ResponseEntity.ok().body(map);
            }else{
                map.put("status", 1);
                map.put("error", "Invalid token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }
    }

}