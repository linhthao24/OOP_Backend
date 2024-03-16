package com.oop.web1.controllers;

import com.oop.web1.routes.RoutesMovie;
import com.oop.web1.routes.Routes;
import com.oop.web1.dto.movieDto.MovieDetailDto;
import com.oop.web1.repositories.MovieRepository;
import com.oop.web1.entitys.MoviesEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import  java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping(value = Routes.BASE_URL)
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    // hom thong tin phim
    @GetMapping(value = RoutesMovie.HOME)
    public ResponseEntity<List<MovieDetailDto>> getAllMovies() {
        try {
            Pageable paging = PageRequest.of(0, 10); // Lấy trang đầu tiên với 10 phần tử
            Page<MoviesEntity> moviesPage = movieRepository.findAll(paging);

            List<MovieDetailDto> movieDetailDtoList = moviesPage.getContent().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(movieDetailDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private MovieDetailDto convertToDto(MoviesEntity moviesEntity) {
        MovieDetailDto movieDetailDto = new MovieDetailDto();
        // Set thông tin từ entity sang DTO
        movieDetailDto.setMovieId(moviesEntity.getMovieId());
        movieDetailDto.setName(moviesEntity.getName());
        movieDetailDto.setDescription(moviesEntity.getDescription());
        movieDetailDto.setDirector(moviesEntity.getDirector());
        movieDetailDto.setActor(moviesEntity.getActor());
        movieDetailDto.setGenre(moviesEntity.getGenre());
        movieDetailDto.setReleaseDate(moviesEntity.getReleaseDate());
        movieDetailDto.setRunningTime(moviesEntity.getRunningTime());
        movieDetailDto.setRating(moviesEntity.getRating());
        movieDetailDto.setPosterUrl(moviesEntity.getPosterUrl());
        movieDetailDto.setTrailerUrl(moviesEntity.getTrailerUrl());
        // Các trường khác cần thiết
        return movieDetailDto;
    }
}
