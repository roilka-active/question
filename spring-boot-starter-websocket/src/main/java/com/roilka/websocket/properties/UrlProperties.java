package com.roilka.websocket.properties;/**
 * Package: com.roilka.websocket.properties
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/6 18:22
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhanghui
 * @description url配置
 * @date 2019/8/6
 */
@ConfigurationProperties("websocket")
@PropertySource(value = {"classpath:config/websocket-${spring.profiles.active}"})
public class UrlProperties {
    private String baseUrl;
    private String shopChatPushData;

    public String getBaseUrl() {
        return baseUrl;
    }

    public UrlProperties setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getShopChatPushData() {
        return baseUrl + shopChatPushData;
    }

    public UrlProperties setShopChatPushData(String shopChatPushData) {
        this.shopChatPushData = shopChatPushData;
        return this;
    }
}
