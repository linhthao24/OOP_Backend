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
public class Auth {
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;
    HashMap<String, Object> map;

    public Auth(AuthenticationRepository authenticationRepository){
        this.authenticationRepository = authenticationRepository;
    }

    public ResponseEntity authorzire(String apiKey) {
        map = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        if (apiKey != null && !apiKey.equals("")) {
            AuthenticationEntity authentication = authenticationRepository.findByApiKey(apiKey);
            if (authentication != null) {
                if (authentication.getExpiredAt().compareTo(Calendar.getInstance().getTime()) > 0) {
                    map.put("status", 0);
                    httpStatus = HttpStatus.OK;
                } else {
                    authenticationRepository.delete(authentication);
                    map.put("status", -4);
                    map.put("error", "Authorization (API Key) has expired");
                    httpStatus = HttpStatus.UNAUTHORIZED;
                }
            }
            else {
                map.put("status", -3);
                map.put("error", "Authorization (API Key) is invalid or User is not logged in");
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
        }
        else {
            map.put("status", -1);
            map.put("error", "Authorization (API Key) is required");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(map, httpStatus);
    }

}
