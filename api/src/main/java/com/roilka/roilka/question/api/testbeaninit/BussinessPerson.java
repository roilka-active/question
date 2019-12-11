package com.roilka.roilka.question.api.testbeaninit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
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
@Scope(value = "prototype")
public class BussinessPerson implements Person, BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean {


    private Animal animal = null;

    public BussinessPerson(@Autowired @Qualifier("dog") Animal animal){
        this.animal = animal;
    }
    @Autowired
    private List<Animal> animalList = new ArrayList<>();


    @Override
    public void service(String s) {
        this.animal.use(s);
        for (Animal a : animalList) {
            a.use(s);
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
