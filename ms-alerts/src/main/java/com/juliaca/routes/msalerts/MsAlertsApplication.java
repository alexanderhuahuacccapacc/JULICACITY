package com.juliaca.routes.msalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsAlertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAlertsApplication.class, args);
    }

}
