package com.roilka.roilka.question.api.config;

import com.roilka.roilka.question.dal.entity.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/29 17:29
 **/
@Configuration
public class AppConfig {
    @Bean(name = "user")
    public User initUser(){
        User user = new User();
        user.setId(11L);
        user.setNote("this is test!");
        user.setUserName("roilka");
        return user;
    }
}
