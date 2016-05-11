package com.looksok.service.rest

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RestTemplatePrototypeSpec extends Specification {

    private RestTemplatePrototype restTemplatePrototype

    void setup() {
        restTemplatePrototype = new RestTemplatePrototype()
    }

    def "GetRestTemplate returns new RestTemplate each time"() {
        when:
        def restTemplate_1 = restTemplatePrototype.getRestTemplate()
        def restTemplate_2 = restTemplatePrototype.getRestTemplate()

        then:
        assert restTemplate_1 instanceof RestTemplate
        assert restTemplate_2 instanceof RestTemplate
        restTemplate_1 != restTemplate_2
    }
}
