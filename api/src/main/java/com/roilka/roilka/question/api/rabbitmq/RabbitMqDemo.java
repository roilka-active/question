package com.roilka.roilka.question.api.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName RabbitMqDemo
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/6/11 20:18
 **/
public class RabbitMqDemo {
    @Autowired
    private AmqpTemplate amqpTemplate;
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new CachingConnectionFactory();
        
    }
}
