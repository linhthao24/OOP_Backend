package com.oop.web1.entitys;

//frame
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "rooms")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RoomsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int room_Id;

    @ManyToOne
    @JoinColumn
    private CinemasEntity cinema;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private String roomType;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<ShowTimesEntity> showTimes;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<SeatsEntity> seats;

}
