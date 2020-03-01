package com.roilka.roilka.question.api.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import static io.lettuce.core.protocol.CommandKeyword.BY;

/**
 * @ClassName HandleRabbitService
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/1/6 14:40
 **/
@Slf4j
public class HandleRabbitService implements ChannelAwareMessageListener {


    /**
     *      * @param
     *      * 1、处理成功，这种时候用basicAck确认消息；
     *      * 2、可重试的处理失败，这时候用basicNack将消息重新入列；
     *      * 3、不可重试的处理失败，这时候使用basicNack将消息丢弃。
     *      *
     *      *  basicNack(long deliveryTag, boolean multiple, boolean requeue)
     *      *   deliveryTag:该消息的index
     *       *  multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
     *        * requeue：被拒绝的是否重新入队列
     *     
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        log.info("接收到消息:" + new String(body));
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(new String(body));
            if (jsonObject.getBoolean("success")) {
                log.info("消息消费成功");

                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认消息消费成功     

            } else if (jsonObject.getBoolean("retry")) {

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

            } else { //消费失败             

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);//消息丢弃
            log.error("This message:" + jsonObject + " conversion JSON error ");
        }

    }
}