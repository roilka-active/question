package com.roilka.roilka.question.api;

import com.roilka.roilka.question.api.controller.RabbitMQTestController;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName RabbitMQTests
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/6/12 11:49
 **/
@MapperScan("com.roilka.roilka.question.dal.dao.zhihu")
@ComponentScan(basePackages = {"com.roilka"},includeFilters = {@ComponentScan.Filter})
@SpringBootTest
public class RabbitMQTests {
    @Autowired
    private RabbitMQTestController rabbitMQTestController;

    @Test
    public void sendMessage(){
        rabbitMQTestController.sendMsg("马失前蹄");
    }
}
