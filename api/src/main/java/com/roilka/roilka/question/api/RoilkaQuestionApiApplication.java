package com.roilka.roilka.question.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = {"com.roilka"},includeFilters = {@ComponentScan.Filter})
@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan("com.roilka.roilka.question.dal.dao.zhihu")
public class RoilkaQuestionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoilkaQuestionApiApplication.class, args);
    }

}
