package com.roilka.roilka.question.api.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMqListen1
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/3 16:44
 **/
@Slf4j
@Component
public class RabbitMqListen1 {

    @RabbitListener(queues = "test")
    public void listen(Message message){
        log.info("当前接收到的信息是，Message={}",message);
        byte[] body = message.getBody();
        String str = new String(body);
            log.info("当前是={}",str );
    }

}
