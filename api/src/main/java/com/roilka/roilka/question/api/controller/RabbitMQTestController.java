package com.roilka.roilka.question.api.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    public static final String EXCHANGE = "roilka-active";
    public static final String ROUTINGKET = "question.test.message";
    public static final String DEFAULT_QUEUE = "question.listen.test.message";

    @Autowired
    private ConnectionFactory connectionFactory;

    @GetMapping("/getName")
    @ApiOperation("测试获取名字")
    public String getName() {
        return "hello";
    }

    @GetMapping("/sendMsg/{message}")
    @ApiOperation("发送消息")
    public Boolean sendMsg(@PathVariable("message") String message) {
        amqpTemplate.convertAndSend("roilka-active", "question.test.message", "convertAndSend" + message);
        amqpTemplate.convertAndSend(EXCHANGE, ROUTINGKET, message, message1 -> message1);
        Object o = amqpTemplate.convertSendAndReceive(EXCHANGE, ROUTINGKET, "convertSendAndReceive" + message);

        log.info("当前消息已发送成功，message={}", message);
        Message receive = amqpTemplate.receive("question.listen.test.message");
        log.info("当前队列《question.listen.test.message》接收到消息为，message： {}", receive);
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel);

        try {
            channel.basicQos(64);
            String m = channel.basicConsume(DEFAULT_QUEUE, defaultConsumer);
            log.info("我是回调，{}", m);

            TimeUnit.SECONDS.sleep(5);
            channel.basicAck(12, true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }


        return true;
    }

}
