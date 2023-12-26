package com.example.reto.meli.springboot.controller;

import com.example.reto.meli.springboot.model.Url;
import com.example.reto.meli.springboot.repository.StatsRepository;
import com.example.reto.meli.springboot.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.example.reto.meli.springboot.utils.Mapper.writeValueAsString;

@RestController
@Slf4j
public class UrlShortener {

    private Map<String, String> cache;
    private static final Integer CACHE_SIZE = 10; // cache Size. 10 to tests cache max capacity reached
    private static final Integer N = 3; // Items to remove from cache when Cache Size is reached. 3 to test cache delete when full
    private Integer cacheUse = 0;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private StatsRepository statsRepository;


    @PostConstruct
    public void initCache() {
        var urlsList = urlRepository.getAllData();

        cache = urlsList.stream().collect(Collectors.toMap(Url::getShortUrl, Url::getLongUrl));

        cacheUse = cache.size();
    }

    @GetMapping("/shorten")
    public String urlShortener(@RequestParam(value = "url") String url) {
        log.info("Long URL: " + url);
        var shortUrl = "";

        var dbShortUrl = urlRepository.getShortUrl(url);

        if(dbShortUrl == null) {
            shortUrl = base10toBase32conversion(urlRepository.getMaxId());
            addToCache(shortUrl, url);
            urlRepository.insertUrl(shortUrl, url);
            statsRepository.createStats(shortUrl);
        } else {
            log.info("URL: {} was already on database.", url);
            shortUrl = dbShortUrl;
        }

        log.info("Short URL: {}", shortUrl);

        return "https://me.li/" + shortUrl;
    }

    @GetMapping("/{shortUrl}")
    public void getLongUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        log.info("Go to long URL. Short URL: " + shortUrl);

        var longUrl = searchUrlOnCache(shortUrl);

        if (longUrl == null) {

            longUrl = searchUrlOnDb(shortUrl);

            if (longUrl == null) {
                log.info("Short URL: {} , NOT FOUND.", shortUrl);
                return;
            } else {
                addToCache(shortUrl, longUrl);
                log.info("Short URL found on data base. URL added to cache.");
            }

        }

        // RUN Thread to update Stats without delaying main Thread
        executor.execute(() -> updateStats(shortUrl));

        log.info("Long URL: " + longUrl);

        response.sendRedirect(longUrl);
    }

    @DeleteMapping("/deleteShortUrl/{shortUrl}")
    public void deleteShortUrl(@PathVariable String shortUrl) {
        log.info("Delete short URL: {}", shortUrl);

        cache.remove(shortUrl);
        urlRepository.deleteShortUrl(shortUrl);
        statsRepository.deleteStats(shortUrl);
    }

    @GetMapping("/stats")
    public String getAllStats() {
        log.info("Get statistics for all URLs");

        var json = writeValueAsString(statsRepository.getAllStats());

        log.info("Obtained Stats: {}", json);
        return json;
    }

    @GetMapping("/stats/{shortUrl}")
    public String getStats(@PathVariable String shortUrl) {
        log.info("Get statistics for all URLs");

        var json = writeValueAsString(statsRepository.getStats(shortUrl));

        log.info("Obtained Stats: {}", json);
        return json;
    }

    private String searchUrlOnCache(String shortUrl) {
        log.info("Search Cache for long URL");
        return cache.get(shortUrl);
    }

    private String searchUrlOnDb(String shortUrl) {
        log.info("Search Data Base for long URL");
        return urlRepository.getLongUrl(shortUrl);
    }
    private String base10toBase32conversion(Integer number) {
        return Integer.toString(number, 36);
    }

    private void addToCache(String shortUrl, String url) {
        if(cacheUse < CACHE_SIZE) {
            cache.put(shortUrl, url);
            cacheUse++;
        } else {
            // free cache space. Remove N elements from the beginning, no Evict Logic applied.
            var iterator = cache.entrySet().iterator();
            var i = 0;
            while (iterator.hasNext() && i<N) {
                iterator.next();
                iterator.remove();
                i++;
            }
            cacheUse-=N;
            cache.put(shortUrl, url);
            cacheUse++;
        }
    }

    private void updateStats(String shortUrl) {
        log.info("New Thread for statistics update.");
        var stats = statsRepository.getStats(shortUrl);
        stats.setHits(stats.getHits()+1);
        stats.setLastHitDate(new Date());
        statsRepository.updateStats(stats);
    }

}
