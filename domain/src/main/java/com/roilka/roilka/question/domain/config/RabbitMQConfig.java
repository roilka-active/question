package com.roilka.roilka.question.domain.config;

import com.roilka.roilka.question.common.utils.OsUtils;
import com.roilka.roilka.question.domain.rabbitmq.HandleRabbitService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName RabbitMQConfig
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/20 10:22
 **/
//@Configuration
public class RabbitMQConfig {


    /*@Value("${mq.rabbit.address}")
    String address;

    @Value("${mq.rabbit.username}")
    String username;

    @Value("${mq.rabbit.password}")

    String password;

    @Value("${mq.rabbit.virtualHost}")

    String mqRabbitVirtualHost;

    @Value("${mq.rabbit.exchange.name}")

    String exchangeName;

    @Value("${mq.rabbit.size}")

    int queueSize;


    @Value("${mq.concurrent.consumers}")


    int concurrentConsumers;

    @Value("${mq.prefetch.count}")
    int prefetchCount;

    //创建mq连接

    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(mqRabbitVirtualHost);
        connectionFactory.setPublisherConfirms(true);
        //该方法配置多个host，在当前连接host down掉的时候会自动去重连后面的host

        connectionFactory.setAddresses(address);
        return connectionFactory;
    }

    //监听处理类
    @Bean
    @Scope("prototype")
    public HandleRabbitService handleService() {
        return new HandleRabbitService();
    }

    //动态创建queue，命名为：hostName.queue1【192.168.1.1.queue1】,并返回数组queue名称

    @Bean
    public String[] mqMsgQueues() throws AmqpException, IOException {
        String[] queueNames = new String[queueSize];
        String hostName = OsUtils.getHostNameForLiunx();//获取hostName
        for (int i = 1; i <= queueSize; i++) {
            String queueName = String.format("%s.queue%d", hostName, i);

            connectionFactory().createConnection().createChannel(false).queueDeclare(queueName, true, false, false, null);
            connectionFactory().createConnection().createChannel(false).queueBind(queueName, exchangeName, queueName);
            queueNames[i - 1] = queueName;
        }
        return queueNames;
    }

    //创建监听器，监听队列
    @Bean
    public SimpleMessageListenerContainer mqMessageContainer(HandleRabbitService handleService) throws AmqpException, IOException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueueNames(mqMsgQueues());
        container.setExposeListenerChannel(true);
        container.setPrefetchCount(prefetchCount);//设置每个消费者获取的最大的消息数量
        container.setConcurrentConsumers(concurrentConsumers);//消费者个数
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式为手工确认
        container.setMessageListener(handleService);//监听处理类
        return container;
    }
*/
}
