package com.roilka.roilka.question.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName CommonProperties
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/6 20:41
 **/
@ConfigurationProperties(prefix = "roilka-common")
public class CommonProperties {

    /**
     * MDC前缀
     */
    private String mdcPrefix;

    public String getMdcPrefix() {
        return mdcPrefix;
    }

    public void setMdcPrefix(String mdcPrefix) {
        this.mdcPrefix = mdcPrefix;
    }
}
