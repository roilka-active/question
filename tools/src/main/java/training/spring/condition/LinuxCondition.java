package training.spring.condition;/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author zhanghui1
 * @date 2020/5/2813:38
 */

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.stream.Stream;

/**
 * @ClassName LinuxCondition
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/28 13:38
 **/
public class LinuxCondition implements Condition {

    /**
     *
     * @param context 判断条件能使用的上下文
     * @param metadata  注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //判断是否是linux系统
        // 能获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //类加载器
        ClassLoader classLoader = context.getClassLoader();

        Environment environment = context.getEnvironment();

        String property = environment.getProperty("os.name");
//        Stream.of(property)
        BeanDefinitionRegistry registry = context.getRegistry();
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEachOrdered(System.out::println);
        return false;
    }
}
