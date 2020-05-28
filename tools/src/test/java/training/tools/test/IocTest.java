package training.tools.test;/**
 * Package: training.tools.spring
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 22:50
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import training.spring.config.MainConfig;
import training.spring.config.MainConfig2;
import training.spring.entity.Person;
import training.tools.spring.ioc.IocContainer;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhanghui
 * @description
 * @date 2019/9/25
 */
public class IocTest {
    private IocContainer iocContainer = new IocContainer();

    /*@Before
    public  void before(){

        iocContainer.setBean(Audi.class,"audi");
        iocContainer.setBean(Buick.class,"buick");
        iocContainer.setBean(ZhangSan.class,"zhangSan","audi");
        iocContainer.setBean(LiSi.class,"liSi","buick");
    }*/

    /*@Test
    public void Test(){
        Humen zhangSan = (Humen) iocContainer.getBean("zhangSan");
        zhangSan.goHome();
        Humen liSi = (Humen) iocContainer.getBean("liSi");
        liSi.goHome();
    }*/
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Bean bean = context.getBean("bean", Bean.class);
        System.out.println("bean=" + bean);
    }

    @Test
    public void test01(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
    }

    /**
     *  验证bean的不同作用域
     * @scope
     *  单例下，使用的是同一个对象
     *  原型下：ioc容器启动的时候并不会去调用方法去创建实例，而是每次获取都会创建一个新的对象
     */
    @Test
    public void test02(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Environment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println("当前系统是："+property.toString());
        Stream.of(beanDefinitionNames).forEach(System.out::println);
        Person bean = applicationContext.getBean(Person.class);
        Person bean2 = applicationContext.getBean(Person.class);
        System.out.println(bean == bean2);
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);


    }
}
