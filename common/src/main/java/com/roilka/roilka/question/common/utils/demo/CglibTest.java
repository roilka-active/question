package com.roilka.roilka.question.common.utils.demo;

import com.roilka.roilka.question.common.javabase.concurrent.entity.HelloWorld;
import org.springframework.cglib.beans.ImmutableBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CglibTest
 * @Description cglib 动态代理
 * @Author 75six
 * @Date 2020/4/14 10:43
 **/
public class CglibTest {
    public static void main(String[] args) throws Exception {
        //testFixedValue();
        //testImmutableBean();
        List<Integer> aList = new ArrayList<>();
        aList.add(1);
        aList.add(3);
        List<Integer> bList = aList.stream().collect(Collectors.toList());
        bList.remove(1);
        System.out.println(3|9);
        testFixedValue();
    }

    public static void testMethodInterceptor(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloWorld.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("Before method run ...");
                Object result = methodProxy.invokeSuper(o, objects);
                System.out.println("after method run ...");
                return result;
            }
        });

        HelloWorld helloWorld = (HelloWorld) enhancer.create();
        //helloWorld.morning("cglib");
        //helloWorld.bye("test", "num", new String[]{"天王盖地虎","宝塔镇河妖"});
        System.out.println(helloWorld.toString());
        System.out.println(helloWorld.getClass());
        System.out.println(helloWorld.hashCode());
    }
    public static void testFixedValue(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloWorld.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hello world!";
            }
        });

        HelloWorld helloWorld = (HelloWorld) enhancer.create();
        //helloWorld.morning("cglib");
        helloWorld.bye("test", "num", new String[]{"天王盖地虎","宝塔镇河妖"});
        System.out.println(helloWorld.toString());
        System.out.println(helloWorld.getClass());
        System.out.println(helloWorld.hashCode());
    }


    public static void testImmutableBean() throws Exception {
        HelloWorld bean = new HelloWorld();
        bean.setDesc("Hello world");
        HelloWorld immutableBean = (HelloWorld) ImmutableBean.create(bean); //创建不可变类
        sysOut("Hello world"+immutableBean.getDesc());
        bean.setDesc("Hello world, again"); //可以通过底层对象来进行修改
        sysOut("Hello world, again"+ immutableBean.getDesc());
        immutableBean.setDesc("Hello cglib"); //直接修改将throw exception

    }

    public static void sysOut(Object obj){
        System.out.println(obj);
    }
}
