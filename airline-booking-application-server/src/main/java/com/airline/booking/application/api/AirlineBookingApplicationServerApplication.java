package com.airline.booking.application.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AirlineBookingApplicationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineBookingApplicationServerApplication.class, args);
    }

}
