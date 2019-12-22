package com.roilka.roilka.question.api.aop;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhanghui
 * @description 统一日志管理
 * @date 2019/12/1
 */
@Slf4j
public abstract class AbstractControllerLogAspect {

    private ThreadLocal<Long> timeLocal = new ThreadLocal<>();

    public abstract void controllerLog();

    @Around(value = "controllerLog()&& @annotation(logController)")
    public Object doArround(ProceedingJoinPoint joinPoint, ApiOperation logController) throws Throwable {

        Object result = null;
        try {

            this.doBefore(joinPoint,logController);

            //执行目标方法
            result = joinPoint.proceed();
            this.doAfterReturning(joinPoint,result,logController);
        }catch (Throwable ex){
            this.doAfterThrowing(joinPoint,logController,ex);
        }

        return null;
    }

    public void doBefore(ProceedingJoinPoint joinPoint, ApiOperation logController) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        MDC.put("url", null == requestAttributes ? "" : ((ServletRequestAttributes) requestAttributes).getRequest().getRequestURI());

        log.info("SC服务开始调用:{}, requestData={}", logController.value(), joinPoint.getArgs());
        //获取进入时间
        timeLocal.set(System.currentTimeMillis());


    }

    public void doAfter() {
        MDC.clear();
    }

    public void doAfterReturning(ProceedingJoinPoint joinPoint,Object result, ApiOperation logController) {
        Long timeTaken = System.currentTimeMillis() - timeLocal.get().longValue();
        MDC.put("timetaken",String.valueOf(timeTaken));
        log.info("RC服务结束调用:{},耗时={}ms,result={}", logController.value(), timeTaken, result);
    }

    public void doAfterThrowing(ProceedingJoinPoint joinPoint, ApiOperation logController, Throwable exception) throws Throwable{

        throw exception;
    }
}
