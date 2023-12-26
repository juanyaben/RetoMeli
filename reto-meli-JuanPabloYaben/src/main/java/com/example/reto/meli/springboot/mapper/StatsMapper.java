package com.example.reto.meli.springboot.mapper;

import com.example.reto.meli.springboot.model.Stats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsMapper implements RowMapper<Stats> {

    @Override
    public Stats mapRow(ResultSet rs, int rowNum) throws SQLException {
        Stats stats = new Stats();
        stats.setId(rs.getLong("ID"));
        stats.setShortUrl(rs.getString("SHORT_URL"));
        stats.setHits(rs.getInt("HITS"));
        stats.setLastHitDate(rs.getDate("LAST_HIT_DATE"));
        return stats;
    }
}
