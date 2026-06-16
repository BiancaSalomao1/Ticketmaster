package com.globaltech.ticketmaster.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

    public static final String TIME_ZONE = "America/Sao_Paulo";

    public static void main(String[] args) {
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone(TIME_ZONE));
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}