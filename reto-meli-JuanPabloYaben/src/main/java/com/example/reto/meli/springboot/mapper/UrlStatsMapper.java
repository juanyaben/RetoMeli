package com.example.reto.meli.springboot.mapper;

import com.example.reto.meli.springboot.model.UrlStats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UrlStatsMapper implements RowMapper<UrlStats> {
    @Override
    public UrlStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        UrlStats urlStats = new UrlStats();
        urlStats.setId(rs.getLong("ID"));
        urlStats.setShortUrl(rs.getString("SHORT_URL"));
        urlStats.setLongUrl(rs.getString("LONG_URL"));
        urlStats.setHits(rs.getInt("HITS"));
        urlStats.setLastHitDate(rs.getDate("LAST_HIT_DATE"));
        return urlStats;
    }
}
