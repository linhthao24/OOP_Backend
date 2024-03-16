package com.oop.web1.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "reviews")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int review_Id;

    //User_Id
    @ManyToOne
    @JoinColumn
    private UsersEntity user;
    //Movie_Id
    @ManyToOne
    @JoinColumn
    private MoviesEntity movie;

    @Column(nullable = false)
    private float rating;

    private String content;
}
