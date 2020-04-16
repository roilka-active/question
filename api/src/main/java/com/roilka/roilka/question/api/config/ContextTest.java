package com.roilka.roilka.question.api.config;

import com.roilka.roilka.question.api.rabbitmq.MqMsg;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName ContextTest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/16 18:44
 **/
public class ContextTest {

    public static void main(String[] args) {
        ApplicationContext ac = new MyClassPathXmlApplicationContext();
         ac.getBean("zhihuInterceptor");
    }
}
