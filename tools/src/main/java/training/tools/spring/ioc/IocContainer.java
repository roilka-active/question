package training.tools.spring.ioc;/**
 * Package: training.tools.spring.ioc
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 23:09
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanghui
 * @description 1. 实例化Bean
 * 2. 保存Bean
 * 3. 提供Bean
 * 4. 每一个Bean要产生一个唯一的ID 与之相对应
 * @date 2019/9/25
 */
public class IocContainer {
    private Map<String, Object> beans = new ConcurrentHashMap<String, Object>();

    public Object getBean(String beanId) {
        return beans.get(beanId);
    }

    /**
     * 委托IOC 容器创建一个Bean
     *
     * @param clazz        要创建Bean的clazz
     * @param beanId
     * @param paramBeanIds 要创建Bean 的class 的构造方法所需要的参数的beanId们
     */
    public void setBean(Class<?> clazz, String beanId, String... paramBeanIds) {
        // 1. 组装构造方法所需要的值
        Object[] paramValues = new Object[paramBeanIds.length];
        for (int i = 0; i < paramValues.length; i++) {
            paramValues[i] = beans.get(paramBeanIds[i]);
        }
        // 2. 调用构造方法实例化Bean
        Object bean = null;
        for (Constructor<?> constructor : clazz.getConstructors()) {
            try {
                bean = constructor.newInstance(paramValues);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        // 3. 将实例化的bean 放入beans
        beans.put(beanId,bean);
    }
}
