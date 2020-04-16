package com.roilka.roilka.question.api.aop.custom;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CustomAopConfig
 * @Description 自定义AOP切面配置
 * @Author zhanghui1
 * @Date 2020/4/15 10:29
 **/
public class CustomAopConfig {

    private Map<Method,CustomAspectRealHandler> points = new HashMap<>();
    public CustomAspectRealHandler getPoints(Method m){
        return this.points.get(m);
    }

    public void addMethod(Method method,Object aspect,Method[] points){
        this.points.put(method, new CustomAspectRealHandler(aspect, points));
    }

    public boolean containMethod(Method m){
        return this.points.containsKey(m);
    }
    //内部对象, 用来存储method对象及需要通知的aspject
    public class CustomAspectRealHandler {
        private Object apsHanderInstance;
        private Method[] handers;

        public CustomAspectRealHandler(Object apsHanderInstance, Method[] handers) {
            this.apsHanderInstance = apsHanderInstance;
            this.handers = handers;
        }

        public Object getApsHanderInstance() {
            return apsHanderInstance;
        }

        public Method[] getHanders() {
            return handers;
        }
    }

}
