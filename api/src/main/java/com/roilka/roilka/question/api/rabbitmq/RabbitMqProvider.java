package com.roilka.roilka.question.api.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName RabbitMqProvider
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/16 19:56
 **/

public class RabbitMqProvider {
    @Autowired
    ConnectionFactory connectionFactory;
    public void senMessage(){

    }
}
