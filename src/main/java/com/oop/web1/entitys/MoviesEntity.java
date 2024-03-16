package com.oop.web1.entitys;

//frame
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "movies")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MoviesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int movieId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String actor;

    @Column(nullable = false)
    private String  genre;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private int runningTime;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private String posterUrl;

    @Column(nullable = false)
    private String trailerUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ReviewsEntity> reviews;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowTimesEntity> showTimes;
}
