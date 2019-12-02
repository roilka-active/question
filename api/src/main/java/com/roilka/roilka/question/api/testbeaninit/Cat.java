package com.roilka.roilka.question.api.testbeaninit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @ClassName Cat
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/2 16:25
 **/
@Slf4j
@Component
@Primary
public class Cat implements Animal {
    @Override
    public void use() {
        log.info("猫{}抓老鼠的", Cat.class.getSimpleName());
    }
}
