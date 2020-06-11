package com.roilka.roilka.question.api.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RabbitMQTestController
 * @Description 接口测试控制器
 * @Author zhanghui1
 * @Date 2020/4/15 17:13
 **/
@Slf4j
@RequestMapping("/rabbitmq/test")
@RestController
public class RabbitMQTestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/getName")
    @ApiOperation("测试获取名字")
    public String getName() {
        return "hello";
    }

    @GetMapping("/sendMsg/{message}")
    @ApiOperation("发送消息")
    public Boolean sendMsg(@PathVariable("message") String message) {
        amqpTemplate.convertAndSend("roilka-active", "question.test.message",message);
        return true;
    }
}
