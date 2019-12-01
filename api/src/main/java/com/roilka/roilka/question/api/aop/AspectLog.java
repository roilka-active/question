package com.roilka.roilka.question.api.aop;/**
 * Package: com.roilka.roilka.question.common.aop
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/11/30 21:51
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AspectLog extends AbstractControllerLogAspect {


    @Pointcut("execution(* com.roilka..*.*(..))")
    @Override
    public void controllerLog() {
    }
}
