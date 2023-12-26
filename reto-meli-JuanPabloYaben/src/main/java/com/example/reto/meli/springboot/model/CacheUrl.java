package com.example.reto.meli.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CacheUrl {

    private String shortUrl;

    private String LongUrl;
}
