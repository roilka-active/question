package com.roilka.roilka.question.api.config;

import ch.qos.logback.classic.turbo.MDCFilter;
import com.roilka.roilka.question.common.filter.DefaultMDCFilter;
import com.roilka.roilka.question.common.properties.CommonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CommonAutoConfiguration
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/6 20:27
 **/
@Configuration
@EnableConfigurationProperties(value = {CommonProperties.class})
public class CommonAutoConfiguration {

    @Autowired
    private CommonProperties commonProperties;
    @Bean
    @ConditionalOnExpression("${common.enable-mdc-filter:true}")
    public FilterRegistrationBean mdcFilterRegistrationBean() {
        DefaultMDCFilter.mdcPrefix = commonProperties.getMdcPrefix();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        DefaultMDCFilter mdcFilter = new DefaultMDCFilter();
        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
