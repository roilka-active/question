package com.roilka.roilka.question.domain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @ClassName MyWebApplInitializer
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/29 13:46
 **/
public class MyWebApplInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final Logger logger = LoggerFactory.getLogger(MyWebApplInitializer.class);
    // Spring IoC 容器配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // 可以返回Spring 的Java配置文件数组
        return new Class<?>[0];
    }

    // DispatcherServlet 的URI 映射关系配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // 可以返回 Spring的Java 配置文件数组
        return new Class[0];
    }

    // DispatchServlet 拦截请求匹配
    @Override
    protected String[] getServletMappings() {

        return new String[0];
    }
}
