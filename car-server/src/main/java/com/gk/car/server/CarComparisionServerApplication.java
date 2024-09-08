package com.gk.car.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.gk.car")
@EnableJpaRepositories(basePackages = "com.gk.car")
@EntityScan(basePackages = "com.gk.car")
@EnableJpaAuditing
public class CarComparisionServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarComparisionServerApplication.class, args);
    }
}