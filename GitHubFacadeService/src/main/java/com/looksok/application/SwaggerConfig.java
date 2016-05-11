package com.looksok.application;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAutoConfiguration
@Profile("test")
@EnableSwagger
public class SwaggerConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
        log.info("Initializing Swagger config");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(new ApiInfo(
                        "GitHub Facade Service API",
                        "This API is for interacting with GitHub Facade service", null, null, null, null))
                .useDefaultResponseMessages(false)
                .includePatterns(".*");
    }
}
