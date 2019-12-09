package com.roilka.roilka.question.api.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zhanghui
 * @description 切面日志
 * @date 2019/11/30
 */
@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE-100)
public class AspectLog extends AbstractControllerLogAspect {


    @Pointcut("execution(* com.roilka..*.*(..))")
    @Override
    public void controllerLog() {
    }
}
