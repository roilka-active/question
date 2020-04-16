package com.roilka.roilka.question.api.config.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName ZhihuInterceptor
 * @Description 知乎拦截器
 * @Author zhanghui1
 * @Date 2020/1/13 14:43
 **/
@Slf4j
@Component
public class ZhihuInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("h哈喽，你被拦截了");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("h哈喽，你被拦截结束了");
        Map<String, Object> map = modelAndView.getModel();
        log.info("视图是：{}",map);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.error("你出异常了哦！", ex);
    }
}
