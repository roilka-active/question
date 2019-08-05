package training.websocket.proxy;/**
 * Package: training.websocket.proxy
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/5 18:32
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhanghui
 * @description
 * @date 2019/8/5
 */
public class ProxyFactory {
    private Object targetObject;//目标对象
    private BeforeAdvice beforeAdvice;//前值增强
    private AfterAdvice afterAdvice;//后置增强

    /**
     * 用来生成代理对象
     * @return
     */
    public Object creatProxy() {
        /**
         * 给出三个参数
         */
        ClassLoader classLoader = this.getClass().getClassLoader();
        //获取当前类型所实现的所有接口类型
        Class[] interfaces = targetObject.getClass().getInterfaces();

        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 在调用代理对象的方法时，会执行这里的内容
                 */
                if(beforeAdvice != null) {
                    beforeAdvice.before();
                }
                Object result = method.invoke(targetObject, args);//调用目标对象的目标方法
                //执行后续增强
                afterAdvice.after();

                //返回目标对象的返回值
                return result;
            }
        };
        /**
         * 2、得到代理对象
         */
        Object proxyObject = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        return proxyObject;

    }
//get和set方法略
}
