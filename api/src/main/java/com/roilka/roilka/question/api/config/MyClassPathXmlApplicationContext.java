package com.roilka.roilka.question.api.config;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyClassPathXmlApplicationContext
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/16 18:36
 **/
//@Component
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
    public MyClassPathXmlApplicationContext(String... configLocation){
        super(configLocation);
    }
    protected void initPropertySources(){
        // 添加验证要求, 检查环境变量中有无VAR，没有就报错
        getEnvironment().setRequiredProperties("VAR");
    }
}
