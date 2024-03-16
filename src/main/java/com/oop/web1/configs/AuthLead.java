package com.oop.web1.configs;

import com.oop.web1.entitys.AuthenticationEntity;
import com.oop.web1.entitys.UsersEntity;
import com.oop.web1.repositories.AuthenticationRepository;
import com.oop.web1.repositories.UserRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;

@Component
public class AuthLead {
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;
    HashMap<String, Object> map;
    public AuthLead(AuthenticationRepository authenticationRepository, UserRepository userRepository){
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity authManager(String apiKey){
        map = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        if(apiKey != null && !apiKey.equals("")){
            AuthenticationEntity authentication = authenticationRepository.findByApiKey(apiKey);
            if(authentication != null){
                Optional<UsersEntity> usersEntityOptional = userRepository.findUsersEntityByUserId(authentication.getUser_Id());
                if(usersEntityOptional.isPresent()){
                    UsersEntity usersEntity = usersEntityOptional.get();
                    if (usersEntity.getRole() == Role.role_manager){
                        map.put("status", 0);
                        httpStatus = HttpStatus.OK;
                    } else if (usersEntity.getRole() == Role.role_admin) {
                        map.put("status", 1);
                        httpStatus = HttpStatus.OK;
                    }else {
                        map.put("status", -1);
                        httpStatus = HttpStatus.UNAUTHORIZED;
                    }
                }
                else{
                    map.put("status", -2);
                    httpStatus = HttpStatus.NOT_FOUND;
                }
            }
            else{
                map.put("status", -3);
                httpStatus = HttpStatus.NOT_FOUND;
            }
        }
        else{
            map.put("status", -4);
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
