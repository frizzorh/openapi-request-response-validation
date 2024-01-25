package com.tweesky.cloudtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.tweesky.cloudtools"})
@EnableConfigurationProperties
public class OpenApiValidatorApplication {

    private final Logger log = LoggerFactory.getLogger(OpenApiValidatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenApiValidatorApplication.class, args);
    }


    @PostConstruct
    public void init() {
        log.info("Proxy Started");
    }
}
