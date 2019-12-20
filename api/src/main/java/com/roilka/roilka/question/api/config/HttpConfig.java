package com.roilka.roilka.question.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName HttpConfig
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/27 17:34
 **/
public class HttpConfig {
    @Autowired
    private RestTemplate restTemplate;


}
