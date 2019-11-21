package com.swapi.challenge.swapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwapiConfig {

    @Value("${swapi.url}")
    private String url;

    public String getUrl() {
        return this.url;
    }

}
