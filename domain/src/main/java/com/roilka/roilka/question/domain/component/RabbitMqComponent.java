package com.roilka.roilka.question.domain.component;

import com.roilka.roilka.question.domain.commontools.rabbitmq.HandleRabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMqComponent
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/1/14 15:06
 **/
@Slf4j
@Component
public class RabbitMqComponent {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.rabbit.exchange.name}")
    private String exchange;
    @Value("${mq.rabbit.routingKey}")
    private String routingKey;



    @Autowired
    private HandleRabbitService handleRabbitService;

    public void convertAndSend(){
        amqpTemplate.convertAndSend(exchange, routingKey, "是真的吗/");
    }

    public void listen(){

    }
}
