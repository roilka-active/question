package com.roilka.roilka.question.domain.config;

import com.roilka.roilka.question.dal.entity.user.User;
import com.roilka.roilka.question.domain.entity.BussinessPerson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/29 17:29
 **/
@ComponentScan(basePackageClasses = BussinessPerson.class,lazyInit = true)
@Configuration
public class AppConfig {
    @Bean(name = "users")
    public User initUser(){
        User user = new User();
        //user.setId(11L);
        user.setNote("this is testbeaninit!");
        user.setUserName("roilka");
        return user;
    }
}
