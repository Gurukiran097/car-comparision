package com.gk.car.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gk")
public class CarComparisionServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarComparisionServerApplication.class, args);
    }
}