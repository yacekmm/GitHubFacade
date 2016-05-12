package com.looksok.service.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateSingleton {

    private static final RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getInstance() {
        return restTemplate;
    }
}
