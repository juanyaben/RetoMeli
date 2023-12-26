package com.example.reto.meli.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RetoMeliSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetoMeliSpringBootApplication.class, args);
    }

}
