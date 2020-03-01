package com.roilka.roilka.question.domain.config;

import com.roilka.roilka.question.common.filter.DefaultMDCFilter;
import com.roilka.roilka.question.common.properties.CommonProperties;
import com.roilka.roilka.question.common.utils.RedisUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName CommonAutoConfiguration
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/6 20:27
 **/
@Configuration
@EnableConfigurationProperties(value = {CommonProperties.class})
public class CommonAutoConfiguration {

    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private CloseableHttpClient          httpClient;

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;
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

    @Bean
    @ConditionalOnMissingBean(name = "commonHttpClient")
    @ConditionalOnExpression("${common.enable-http-client:true}")
    public CloseableHttpClient initHttpClient(){
        CloseableHttpClient httpClient = HttpClients.custom().build();
        return httpClient;
    }

    @ConditionalOnProperty(prefix = "spring.redis", value = "prefix", matchIfMissing = false)
    @Bean
    public RedisUtils initRedis(StringRedisTemplate redis, @Value("${spring.redis.prefix}") String redisPrefix,
                                @Value("${spring.redis.lettuce.validateConnection:true}") boolean validateConnection) {
        lettuceConnectionFactory.setValidateConnection(validateConnection);
        return new RedisUtils().getInstance(redis, redisPrefix);
    }

    @Bean
        //    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    /**
     *  线程池
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("hello-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
