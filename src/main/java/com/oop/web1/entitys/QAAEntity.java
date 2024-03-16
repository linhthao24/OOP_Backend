package com.oop.web1.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "QAAs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QAAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int qAA_Id;

    private String question;

    private String answer;
}
