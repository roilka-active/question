package training.tools.spring.ioc.color;
/**
 * Package: training.tools.spring.ioc.color
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/17 23:26
 * <p>
 * Modified By:
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

/**
 * @author zhanghui
 * @description
 * @date 2020/6/17
 */
public class Red implements ApplicationContextAware , BeanNameAware
, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的ioc：" + applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("name= "+name);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String s = stringValueResolver.resolveStringValue("你好${os.name},我是${3*6}");
        System.out.println("解析的字符串："+s);

    }
}
