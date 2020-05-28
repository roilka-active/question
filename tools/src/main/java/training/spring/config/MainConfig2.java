package training.spring.config;/**
 * Package: com.roilka.roilka.question.common.spring.config
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/27 23:51
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import training.spring.entity.Person;

/**
 * @author zhanghui
 * @description bean
 * @date 2020/5/27
 */
@Configuration
@ComponentScan(value = "training.spring",excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Controller.class)})
public class MainConfig2 {

//    @Scope(value = "prototype")
//    @Lazy
    @Bean("person")
    @Primary
    public Person person2(){
        System.out.println("ioc 开始创建对象。。。");
        return new Person("lisi",20);
    }

    @Bean("mayun")
    public Person person3(){
        System.out.println("ioc 开始创建对象mayun。。。");
        return new Person("mayun",50);
    }

    @Bean("leijun")
    public Person person4(){
        System.out.println("ioc 开始创建对象leijun。。。");
        return new Person("leijun",46);
    }
}
