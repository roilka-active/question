package com.roilka.roilka.question.api.config;

import com.roilka.roilka.question.api.testbeaninit.BussinessPerson;
import com.roilka.roilka.question.dal.entity.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/29 17:29
 **/
@ComponentScan(basePackageClasses = BussinessPerson.class)
@Configuration
public class RedisInitConfig {
    @Bean(name = "user1")
    public User initUser(){
        User user = new User();
        //user.setId(11L);
        user.setNote("this is testbeaninit!");
        user.setUserName("roilka");
        return user;
    }
}
