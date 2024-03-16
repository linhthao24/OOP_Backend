package com.oop.web1.dto.movieDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieDetailDto {
    int movieId;
    String name;
    String description;
    String director;
    String actor;
    String  genre;
    LocalDate releaseDate;
    int runningTime;
    float rating;
    String posterUrl;
    String trailerUrl;
}
