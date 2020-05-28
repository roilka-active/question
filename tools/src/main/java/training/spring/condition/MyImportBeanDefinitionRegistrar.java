package training.spring.condition;/**
 * Package: training.spring.condition
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/29 0:02
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import training.spring.Bean.RainRow;

/**
 * @author zhanghui
 * @description
 * @date 2020/5/29
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @param annotationMetadata 当前类的注解信息
     * @param beanDefinitionRegistry    BeanDefinition 注册类
     *                                  把所有需要添加到容器的Bean，调用BeanDefinitionRegistry 手工注册进来
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean red = beanDefinitionRegistry.containsBeanDefinition("training.spring.Bean.Red");
        boolean blue = beanDefinitionRegistry.containsBeanDefinition("training.spring.Bean.Blue");
        if (red && blue){
            // 指定Bean的定义信息，类型
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainRow.class);
            // 注册Bean
            beanDefinitionRegistry.registerBeanDefinition("rainBow",rootBeanDefinition);
        }
    }
}
