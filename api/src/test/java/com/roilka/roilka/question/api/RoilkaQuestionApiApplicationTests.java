package com.roilka.roilka.question.api;


import com.roilka.roilka.question.api.controller.zhihu.ZhiHuController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.net.URISyntaxException;
@ComponentScan(basePackages = {"com.roilka"},includeFilters = {@ComponentScan.Filter})
@SpringBootTest
class RoilkaQuestionApiApplicationTests {

    @Autowired
    private ZhiHuController zhiHuController;
    @Test
    void contextLoads() throws URISyntaxException {
        zhiHuController.flushArea();
    }

}
