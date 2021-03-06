package com.roilka.roilka.question.domain.config;/**
 * Package: com.roilka.roilka.question.domain.config
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/3/2 1:02
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.roilka.question.domain.service.base.TestService;
import com.roilka.roilka.question.domain.service.openapi.TestServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author zhanghui
 * @description MyBeanFactoryPostProcessor
 * @date 2020/3/2
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("usersInfoService");
        beanDefinition.getDependsOn();
        TestService testService = new TestServiceImpl();
        testService.say();
    }
}
