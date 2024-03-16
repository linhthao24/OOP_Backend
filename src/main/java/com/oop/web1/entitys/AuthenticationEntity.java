package com.oop.web1.entitys;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
@Entity(name = "auth")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int auth_Id;

    @Column(name = "user_id", nullable = false)
    private int user_Id;

    @Column(name = "issued_at", nullable = false)
    @CreatedDate
    private Date issuedAt;

    @Column(name = "expired_at", nullable = false)
    private Date expiredAt;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

}
