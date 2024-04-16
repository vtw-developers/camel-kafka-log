package com.example.cameltest;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelOpenTelemetry
@SpringBootApplication
public class CamelKafkaLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelKafkaLogApplication.class, args);
    }

}
