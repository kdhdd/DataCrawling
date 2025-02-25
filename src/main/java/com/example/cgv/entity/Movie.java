package com.example.cgv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double bookingRate;
    private String openDate;
}
