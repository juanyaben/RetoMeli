package com.example.reto.meli.springboot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class Mapper {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T>String writeValueAsString(T object) {
        return MAPPER.writeValueAsString(object);
    }

}
