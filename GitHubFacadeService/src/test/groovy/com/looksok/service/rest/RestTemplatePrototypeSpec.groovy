package com.looksok.service.rest

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RestTemplatePrototypeSpec extends Specification {

    private RestTemplateSingleton restTemplatePrototype

    void setup() {
        restTemplatePrototype = new RestTemplateSingleton()
    }

    def "GetRestTemplate returns new RestTemplate each time"() {
        when:
        def restTemplate_1 = restTemplatePrototype.getInstance()
        def restTemplate_2 = restTemplatePrototype.getInstance()

        then:
        assert restTemplate_1 instanceof RestTemplate
        assert restTemplate_2 instanceof RestTemplate
        restTemplate_1 == restTemplate_2
    }
}
