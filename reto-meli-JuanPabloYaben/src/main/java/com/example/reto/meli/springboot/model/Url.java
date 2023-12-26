package com.example.reto.meli.springboot.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "URL")
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 10)
    private String shortUrl;

    @Column(nullable = false)
    private String longUrl;

}
