package com.roilka.roilka.question.api.test;

import com.roilka.roilka.question.api.config.AppConfig;
import com.roilka.roilka.question.dal.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName BeanTest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/29 19:07
 **/
public class BeanTest {
    private static Logger logger = LoggerFactory.getLogger(BeanTest.class);

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = ctx.getBean(User.class);
        logger.info("user.id={}", user.getId());
    }
}
