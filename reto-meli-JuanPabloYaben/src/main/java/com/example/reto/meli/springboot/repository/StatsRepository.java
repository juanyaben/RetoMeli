package com.example.reto.meli.springboot.repository;

import com.example.reto.meli.springboot.model.Stats;
import com.example.reto.meli.springboot.model.UrlStats;

import java.util.List;


public interface StatsRepository {

    void createStats(String shortUrl);
    List<UrlStats> getAllStats();
    Stats getStats(String shortUrl);
    void updateStats(Stats stats);
    void deleteStats(String shortUrl);

}
