package com.oop.web1.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity(name = "tickets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int ticket_Id;

    // User_Id
    @ManyToOne
    @JoinColumn
    private UsersEntity user;
    // ShowTime_Id
    @ManyToOne
    @JoinColumn
    private ShowTimesEntity showTime;
    // Seat_Id
    @OneToOne
    @JoinColumn
    private SeatsEntity seat;

    private int quantity;

    private long totalPrice;

    private String paymentMethod;

    private boolean status;


}
