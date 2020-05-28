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
import training.spring.Bean.Color;
import training.spring.Bean.ColorFactoryBean;
import training.spring.Bean.Red;
import training.spring.condition.LinuxCondition;
import training.spring.condition.MyImportBeanDefinitionRegistrar;
import training.spring.condition.MyImportSelector;
import training.spring.condition.WindowsCondition;
import training.spring.Bean.Person;

/**
 * @author zhanghui
 * @description bean
 * @date 2020/5/27
 */
@Configuration
@ComponentScan(value = "training.spring",excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Controller.class)})

@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

//    @Scope(value = "prototype")
//    @Lazy
    @Bean("person")
    @Primary
    public Person person2(){
        System.out.println("ioc 开始创建对象。。。");
        return new Person("lisi",20);
    }

    @Conditional(WindowsCondition.class)
    @Bean("mayun")
    public Person person3(){
        System.out.println("ioc 开始创建对象mayun。。。");
        return new Person("mayun",50);
    }

    @Conditional(LinuxCondition.class)
    @Bean("leijun")
    public Person person4(){
        System.out.println("ioc 开始创建对象leijun。。。");
        return new Person("leijun",46);
    }

    /**
     *  给容器中注册组件
     *  1）包扫描+组件标注注解（@Controller、@Service、@Repository、@Component [自己定义的]
     *  2）@Bean[导入第三方包里面的组件]
     *  3）@Import[快速给容器中导入一个组件]
     *      1》 @Import(要导入到容器的组件），容器就会自动注册到这个组件，id默认是全类名
     *      2》 ImportSelector：返回需要导入的组件全类名数组
     *      3》 ImportBeanDefinitionRegistrar
     *  4）使用Spring提供的FactoryBean（工厂Bean）；
     *      1》 默认获取到的是工厂bean调用getObject创建的对象
     *      2》 要获取工厂Bean本身，我们需要给id前面加上一个&
     *          &colorFactoryBean
     */

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
