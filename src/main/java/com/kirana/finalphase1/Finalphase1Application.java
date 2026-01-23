package com.kirana.finalphase1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * The type Finalphase 1 application.
 */
@SpringBootApplication
@EnableMongoAuditing
public class Finalphase1Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Finalphase1Application.class, args);
    }
}
