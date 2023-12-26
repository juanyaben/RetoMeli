package com.example.reto.meli.springboot.repository;

import com.example.reto.meli.springboot.mapper.UrlMapper;
import com.example.reto.meli.springboot.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UrlRepositoryImp implements UrlRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void insertUrl(String shortUrl, String url) {
        jdbcTemplate.update(
                "INSERT INTO URL (SHORT_URL, LONG_URL)" +
                        "VALUES (?, ?)", shortUrl, url);

    }

    public String getLongUrl(String shortUrl) {
        Object[] args = {shortUrl};

        List<Url> urlsList = jdbcTemplate.query("SELECT * FROM url WHERE SHORT_URL = ?", args, new UrlMapper());

        return urlsList.isEmpty() ? null : urlsList.get(0).getLongUrl();
    }

    public String getShortUrl(String longUrl) {
        Object[] args = {longUrl};

        List<Url> urlsList = jdbcTemplate.query("SELECT * FROM url WHERE LONG_URL = ?", args, new UrlMapper());

        return urlsList.isEmpty() ? null : urlsList.get(0).getShortUrl();
    }

    public List<Url> getAllData() {
        Object[] args = {};

        List<Url> urlsList = jdbcTemplate.query("SELECT * FROM url ORDER BY ID ASC", args, new UrlMapper());

        return urlsList;
    }


    public Integer getMaxId() {
        Integer maxId = jdbcTemplate.queryForObject("SELECT MAX(ID) FROM url", Integer.class);
        return maxId == null ? 0 : maxId;
    }

    public void deleteShortUrl(String shortUrl) {
        Object[] args = {shortUrl};
        jdbcTemplate.update("DELETE FROM url WHERE SHORT_URL = ?", args);
    }
}
