package com.roilka.roilka.question.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.roilka"})
@SpringBootApplication
public class RoilkaQuestionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoilkaQuestionApiApplication.class, args);
    }

}
