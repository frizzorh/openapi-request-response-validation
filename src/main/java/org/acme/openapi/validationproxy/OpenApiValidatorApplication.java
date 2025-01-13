package org.acme.openapi.validationproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class OpenApiValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiValidatorApplication.class, args);
    }

}
