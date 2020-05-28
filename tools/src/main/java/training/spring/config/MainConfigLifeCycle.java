package training.spring.config;/**
 * Package: training.spring.config
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/29 0:24
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import training.spring.Bean.Car;

/**
 *  bean的创建---初始化----销毁的过程
 *  容器管理Bean的生命周期
 *  我们可以自定义初始化和销毁方法,容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 *
 *  构造（对象构建
 *          单实例：在容器启动的时候创建对象
 *          多实例：在每次获取的时候创建对象
 *  初始化：
 *         对象创建完成，并赋值好，调用初始化方法
 *
 *  销毁：
 *          单实例：容器关闭的时候
 *          多实例：容器不会管理这个Bean，容器不会调用销毁方法
 *  1）、指定初始化和销毁方法：
 *          通过@Bean指定init-method和destory-method
 *  2）、通过让Bean实现InitializingBean（定义初始化逻辑）
 *          DispossableBean(定义销毁逻辑）
 *  3）、可以使用JSR250
 *          @PostConstruct:在bean创建完成并且属性设置完成
 *          @PreDestory:在容器销毁Bean之前通知我们进行清理工作
 *  4）、BeanPostProcessor【interface】，bean的后置处理器
 *          在bean初始化前后进行一些处理工作
 *          postProcessBeforeInitialization:
 *          postProcessAfterInitialization:
 * @author zhanghui
 * @description bean的生命周期
 * @date 2020/5/29
 */
@ComponentScan("training.spring.Bean")
@Configuration
public class MainConfigLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destory")
    public Car car(){
        return new Car();
    }
}
