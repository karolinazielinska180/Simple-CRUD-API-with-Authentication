package com.example.simplecrudapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.simplecrudapi")
public class SimpleCrudApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCrudApiApplication.class, args);
    }

}
