package com.roilka.roilka.question.api.testbeaninit;/**
 * Package: com.roilka.roilka.question.api.testbeaninit
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/11/30 18:23
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghui
 * @description
 * @date 2019/11/30
 */
@Slf4j
@Component
public class BussinessPerson implements Person, BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean {


    private Animal animal = null;

    @Autowired
    private List<Animal> animalList = new ArrayList<>();

    @Override
    public void service() {
        this.animal.use();
        for (Animal a : animalList) {
            a.use();
        }
    }

    @Override
    @Autowired
    @Qualifier("dog")
    public void setAnimal(Animal animal) {
        System.out.println("依赖注入");
        this.animal = animal;
    }

    @Override
    public void setAnimalList(List<Animal> animalList) {
        System.out.println("批量依赖注入");
        this.animalList = animalList;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("setBeanFactory,beanFactory={},animal={}", beanFactory,this.animal == null ? null : this.animal.getClass().getSimpleName());
    }

    @Override
    public void setBeanName(String name) {
        log.info("setBeanFactory,name={}，animal={}", name,this.animal == null ? null : this.animal.getClass().getSimpleName());
    }

    @Override
    public void destroy() throws Exception {
        log.info("destroy,animal={}", this.animal == null ? null : this.animal.getClass().getSimpleName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet,animal={}", this.animal == null ? null : this.animal.getClass().getSimpleName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("setApplicationContext,applicationContext={},animal={}", applicationContext,this.animal == null ? null : this.animal.getClass().getSimpleName());
    }
}
