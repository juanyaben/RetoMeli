package com.example.reto.meli.springboot.repository;

import com.example.reto.meli.springboot.mapper.StatsMapper;
import com.example.reto.meli.springboot.mapper.UrlStatsMapper;
import com.example.reto.meli.springboot.model.Stats;
import com.example.reto.meli.springboot.model.UrlStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsRepositoryImp implements StatsRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createStats(String shortUrl) {
        jdbcTemplate.update(
                "INSERT INTO STATS (HITS, SHORT_URL)" +
                        "VALUES (?, ?)", 0, shortUrl);

    }
    public List<UrlStats> getAllStats() {
        return jdbcTemplate.query("SELECT * FROM URL INNER JOIN STATS ON URL.SHORT_URL = STATS.SHORT_URL", new UrlStatsMapper());
    }

    public Stats getStats(String shortUrl) {
        Object[] args = {shortUrl};
        List<Stats> stats = jdbcTemplate.query("SELECT * FROM STATS WHERE STATS.SHORT_URL = ?", args, new StatsMapper());
        return stats.isEmpty() ? new Stats() : stats.get(0);
    }

    public void updateStats(Stats stats) {
        Object[] args = {stats.getHits(), stats.getLastHitDate(), stats.getShortUrl()};
        jdbcTemplate.update("UPDATE STATS SET STATS.HITS = ?, STATS.LAST_HIT_DATE = ? WHERE STATS.SHORT_URL = ?", args);
    }


    public void deleteStats(String shortUrl) {
        Object[] args = {shortUrl};
        jdbcTemplate.update("DELETE FROM stats WHERE SHORT_URL = ?", args);
    }

}
