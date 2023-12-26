package com.example.reto.meli.springboot.repository;

import com.example.reto.meli.springboot.model.Url;

import java.util.List;

public interface UrlRepository {

    void insertUrl(String shortUrl, String url);
    String getLongUrl(String shortUrl);
    String getShortUrl(String longUrl);
    List<Url> getAllData();
    Integer getMaxId();
    void deleteShortUrl(String shortUrl);
}
