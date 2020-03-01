package com.roilka.roilka.question.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhanghui
 * @description 狗
 * @date 2019/11/30
 */
@Slf4j
@Component()
public class Dog implements Animal {
    private static String Temp = "I am A!";
    @Override
    public void use(String a) {
        Temp = a;
        log.info("狗{} 是看门用的", Dog.class.getSimpleName());
    }
}
