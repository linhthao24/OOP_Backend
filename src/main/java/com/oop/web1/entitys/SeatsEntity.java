package com.oop.web1.entitys;

import jakarta.persistence.*;
import lombok.*;
@Entity(name = "seats")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int seat_Id;

    // Room_Id
    @ManyToOne
    @JoinColumn
    private RoomsEntity room;

    @Column(nullable = false)
    private String seatType;

    @Column(nullable = false)
    private char rowName;

    @Column(nullable = false)
    private int columnName;

    private boolean status;

    @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL)
    private TicketsEntity ticket;
}
