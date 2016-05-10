package com.looksok.service.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplatePrototype {

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
