package training.tools.test;/**
 * Package: training.tools.test
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/29 0:29
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import training.spring.Bean.Car;
import training.spring.config.MainConfigLifeCycle;

/**
 * @author zhanghui
 * @description bean的生命周期测试
 * @date 2020/5/29
 */
public class IOCTest_LifeCycle {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
        System.out.println(applicationContext.getBeanDefinitionNames());
        applicationContext.close();
    }
}
