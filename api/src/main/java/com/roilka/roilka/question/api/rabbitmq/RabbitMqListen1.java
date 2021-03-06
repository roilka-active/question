package com.roilka.roilka.question.api.rabbitmq;

import com.roilka.roilka.question.common.utils.JsonConvertUtils;
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

    //@RabbitListener(queues = "test")
    public void listen(MqMsg<String> mqMsg, Message message) {
        log.info("当前接收到的信息是，Message={}", message);
        byte[] body = message.getBody();
        String str = new String(body);
        log.info("当前是={}", str);
    }
    @RabbitListener(queues = "question.listen.test1.message")
    public void test1Msg(String message) {
        log.info("当前接收到的信息是，message：{}", message);

    }
    @RabbitListener(queues = "question.listen.test.message")
    public void test2Msg(String message) {
        log.info("当前接收到的信息是，message2：{}", message);

    }
    @RabbitListener(queues = "question.listen.test.message")
    public void testMsg(String message) {
        log.info("当前接收到的信息是，message：{}", message);

    }
}
