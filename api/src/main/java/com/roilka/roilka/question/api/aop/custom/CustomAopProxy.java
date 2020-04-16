package com.roilka.roilka.question.api.aop.custom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName CustomAopProxy
 * @Description 自定义AOP切面
 * @Author zhanghui1
 * @Date 2020/4/15 10:28
 **/
public class CustomAopProxy implements InvocationHandler {

    private CustomAopConfig config = new CustomAopConfig();
    private Object target;

    public Object getProxy(Object instance) {
        this.target = instance;
        Class<?> clazz = instance.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 当前Method 是代理后的Method
        Method originalMethod = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());
        // 如果动态代理对象的方法属于config中配置的方法，则调用前置通知
        if (config.containMethod(originalMethod)){
            CustomAopConfig.CustomAspectRealHandler handler = this.config.getPoints(originalMethod);
            handler.getHanders()[0].invoke(handler.getApsHanderInstance(), args);
        }
        Object result = method.invoke(target, args);

        //如果动态代理对象的方法 属于config中配置的方法，则调用后置通知
        if (config.containMethod(originalMethod)){
            CustomAopConfig.CustomAspectRealHandler handler = this.config.getPoints(originalMethod);
            handler.getHanders()[1].invoke(handler.getApsHanderInstance(), args);
        }

        return result;
    }
}
