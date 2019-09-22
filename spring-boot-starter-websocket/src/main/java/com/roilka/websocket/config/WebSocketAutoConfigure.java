package com.roilka.websocket.config;/**
 * Package: com.roilka.websocket.config
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/6 18:28
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.websocket.properties.UrlProperties;
import com.roilka.websocket.service.ExampleService;
import com.roilka.websocket.service.WebSocketService;
import com.roilka.websocket.service.WebsocketIntereptor;
import com.roilka.websocket.service.impl.WebsocketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanghui
 * @description 自动配置类
 * @date 2019/8/6
 */
@Configuration
@EnableConfigurationProperties(UrlProperties.class)
@ConditionalOnClass(ExampleService.class)
@ConditionalOnProperty(prefix = "spring.person", value = "enabled", matchIfMissing = true)
public class WebSocketAutoConfigure {

    @Autowired
    private UrlProperties urlProperties;

    @Bean
    @ConditionalOnMissingBean(ExampleService.class)
    ExampleService exampleService(){
        return new ExampleService(urlProperties.getBaseUrl(),urlProperties.getShopChatPushData());
    }

    @Bean
    @ConditionalOnMissingBean(WebSocketService.class)
    WebSocketService webSocketService(){
        return new WebsocketServiceImpl(urlProperties.getShopChatPushData());
    }

    @Bean
    @ConditionalOnMissingBean(ExampleService.class)
    WebsocketIntereptor WebsocketIntereptor(){
        return new WebsocketIntereptor();
    }
}
