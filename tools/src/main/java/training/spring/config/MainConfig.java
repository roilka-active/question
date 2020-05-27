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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
public class MainConfig {
    @Bean
    public Person person(){
        return new Person("lisi",20);
    }
}
