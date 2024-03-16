package com.oop.web1.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity(name = "cinemas")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CinemasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int cinema_Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String logoUrl;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<ShowTimesEntity> showTimes;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<RoomsEntity> rooms;
}
