package com.oop.web1.controllers;

import com.oop.web1.configs.Role;
import com.oop.web1.dto.LoginDto;
import com.oop.web1.dto.managerDto.ListUserDto;
import com.oop.web1.entitys.MoviesEntity;
import com.oop.web1.entitys.UsersEntity;
import com.oop.web1.routes.Routes;
import com.oop.web1.routes.RoutesMovie;
import com.oop.web1.repositories.MovieRepository;
import com.oop.web1.dto.movieDto.MovieDetailDto;
import com.oop.web1.configs.AuthLead;
import com.oop.web1.repositories.AuthenticationRepository;
import com.oop.web1.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = RoutesMovie.MOVIE)
public class CinemaController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    HashMap<String, Object> map;

    // C: them phim
    @PostMapping(value = RoutesMovie.UPLOAD)
    public ResponseEntity listUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody MovieDetailDto movieDetailDto) {
        String token = authorizationHeader.substring("bearer ".length()).trim();
        System.out.println(token);
        AuthLead auth = new AuthLead(authenticationRepository, userRepository);
        ResponseEntity response = auth.authManager(token);
        // System.out.println(response);
        map = (HashMap<String, Object>) response.getBody();
        // System.out.println(response.getStatusCode().is2xxSuccessful());
        if((int) map.get("status") == 0 || (int) map.get("status") == 1){
            MoviesEntity movieEntity = new MoviesEntity();
            movieEntity.setName(movieDetailDto.getName());
            movieEntity.setDescription(movieDetailDto.getDescription());
            movieEntity.setDirector(movieDetailDto.getDirector());
            movieEntity.setActor(movieDetailDto.getActor());
            movieEntity.setGenre(movieDetailDto.getGenre());
            movieEntity.setReleaseDate(movieDetailDto.getReleaseDate());
            movieEntity.setRunningTime(movieDetailDto.getRunningTime());
            movieEntity.setRating(movieDetailDto.getRating());
            movieEntity.setPosterUrl(movieDetailDto.getPosterUrl());
            movieEntity.setTrailerUrl(movieDetailDto.getTrailerUrl());

            // Lưu đối tượng phim vào cơ sở dữ liệu
            MoviesEntity savedMovie = movieRepository.save(movieEntity);

            // Trả về phản hồi với mã HTTP 201 (Created) và thông tin của phim đã được tạo
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
        } else {
            // Trường hợp không có quyền hoặc token không hợp lệ, trả về lỗi 403 (Forbidden)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
