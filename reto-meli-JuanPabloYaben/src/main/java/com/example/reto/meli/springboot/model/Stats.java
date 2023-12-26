package com.example.reto.meli.springboot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "STATS")
@Data
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 10)
    private String shortUrl;

    @Column
    private Integer hits;

    @Column
    private Date lastHitDate;
}
