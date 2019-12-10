package com.roilka.roilka.question.api.config;

import com.roilka.roilka.question.api.testbeaninit.BussinessPerson;
import com.roilka.roilka.question.dal.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/29 17:29
 **/
@SpringBootApplication(scanBasePackageClasses = RedisInitConfig.class)
public class RedisInitConfig {

    // 注入RedisTemplete
    @Autowired
    private RedisTemplate redisTemplate = null;

    @PostConstruct
    public void init() {
        initRedisTemplete();
    }

    private void initRedisTemplete() {
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
    }
}