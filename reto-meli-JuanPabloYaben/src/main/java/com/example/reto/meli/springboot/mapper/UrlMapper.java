package com.example.reto.meli.springboot.mapper;

import com.example.reto.meli.springboot.model.Url;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UrlMapper implements RowMapper<Url> {

    @Override
    public Url mapRow(ResultSet rs, int rowNum) throws SQLException {
        Url url = new Url();
        url.setId(rs.getLong("ID"));
        url.setLongUrl(rs.getString("LONG_URL"));
        url.setShortUrl(rs.getString("SHORT_URL"));
        return url;
    }
}
