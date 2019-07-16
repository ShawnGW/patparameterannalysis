package com.vtest.it.patparameterannalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PatparameterannalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatparameterannalysisApplication.class, args);
    }

}
