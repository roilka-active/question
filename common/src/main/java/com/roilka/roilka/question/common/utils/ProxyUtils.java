package com.roilka.roilka.question.common.utils;

import com.roilka.roilka.question.common.concurrent.entity.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * @ClassName ProxyUtils
 * @Description TODO
 * @Author 75six
 * @Date 2020/4/13 18:42
 **/
public class ProxyUtils {
    public static void main(String[] args) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println(method);
                if (method.getName().equals("morning")) {
                    System.out.println("Hello ," + args[0]);
                }
                return null;
            }
        };

        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),//传入 ClassLoader
                new Class[]{Hello.class}, // 传入要实现的接口
                handler); // 传入处理调用方法的 InvocationHandler
        hello.morning("haha");
        ClassLoader classLoader1 = Hello.class.getClassLoader();
        System.out.println(classLoader1.toString());
        ClassLoader classLoader2 = classLoader1.getParent();
        System.out.println(classLoader2.toString());
        ClassLoader classLoader3 = classLoader2.getParent();
        //System.out.println(classLoader3.toString());
        ArrayList.class.getClassLoader();
    }
}
