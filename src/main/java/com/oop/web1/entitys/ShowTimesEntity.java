package com.oop.web1.entitys;

// build
//frame
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Entity(name = "showTimes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ShowTimesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int showTimes_Id;

    //Movie_Id
    @ManyToOne
    @JoinColumn
    private MoviesEntity movie;
    //Cinema_Id
    @ManyToOne
    @JoinColumn
    private CinemasEntity cinema;
    //Room_Id
    @ManyToOne
    @JoinColumn
    private RoomsEntity room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int availableSeats;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    private List<TicketsEntity> ticket;
}
