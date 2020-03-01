package com.roilka.roilka.question.domain.entity;

import com.roilka.roilka.question.domain.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName BeanTest
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/29 19:07
 **/
public class BeanTest {
    private static Logger logger = LoggerFactory.getLogger(BeanTest.class);

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(ctx.getBean(BussinessPerson.class));
        System.out.println(ctx.getBean(BussinessPerson.class));
        Person person = ctx.getBean(BussinessPerson.class);
        person.service("s");
       /*String a = "tt";
       String b = "aa";
       System.out.println(String.format("%S(%s)",a,b));
       Integer c = 8;
       System.out.println(String.format("%d 小时",c));*/
    }
}
