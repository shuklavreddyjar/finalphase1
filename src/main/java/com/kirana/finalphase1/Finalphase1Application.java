package com.kirana.finalphase1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class Finalphase1Application {

    public static void main(String[] args) {
        SpringApplication.run(Finalphase1Application.class, args);
    }
}
