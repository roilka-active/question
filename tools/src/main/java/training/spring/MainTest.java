package training.spring;/**
 * Package: com.roilka.roilka.question.common.spring
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/27 23:58
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import training.spring.config.MainConfig;
import training.spring.config.MainConfig2;
import training.spring.entity.Person;

import java.util.stream.Stream;

/**
 * @author zhanghui
 * @description 测试类
 * @date 2020/5/27
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = applicationContext.getBean(Person.class);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        System.out.println(bean);
        Stream.of(beanNamesForType).forEach(record -> System.out.println(record));
    }
}
