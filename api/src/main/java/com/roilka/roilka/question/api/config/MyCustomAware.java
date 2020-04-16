package com.roilka.roilka.question.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @ClassName MyCustomAware
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/16 17:31
 **/
@Slf4j
public class MyCustomAware implements ApplicationEventPublisherAware, BeanFactoryAware, ApplicationContextAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("...MyCustomAware : BeanFactory={}",beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("...MyCustomAware : applicationContext={}",applicationContext);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("...MyCustomAware : applicationEventPublisher={}",applicationEventPublisher);
    }
}
