package com.example.reto.meli.springboot.model;

import lombok.Data;

import java.util.Date;

@Data
public class UrlStats {

    private Long id;

    private String shortUrl;

    private String longUrl;

    private Integer hits;

    private Date lastHitDate;

}
