package com.roilka.roilka.question.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.roilka.roilka.question.common.utils.demo.Inventor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MDCUtils
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/9 11:50
 **/
@Slf4j
public class MDCUtils implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(MDCUtils.class);
    /**
     * 链路跟踪编号
     */
    public static final String                                         REQ_KEY           = "reqKey";
    /**
     * IP地址
     */
    public static final String                                         IP                = "ip";
    /**
     * 当前服务名称
     */
    public static final String                                         APPLICATION_NAME  = "applicationName";
    /**
     * RequestID
     */
    public static final String                                         REQUEST_ID        = "RequestID";
    /**
     * remotename
     */
    public static final String                                         REMOTE_NAME       = "remotename";
    /**
     * remoteendpoint
     */
    public static final String                                         REMOTE_ENDPOINT   = "remoteendpoint";
    public static final String                                         TRACE_NO          = "traceNo";
    public static final String                                         SC_CLIENT_IP      = "scClientIp";
    /***
     * 当前请求上下文中的头部信息copy
     */
    public static final String                                         COPY_OF_HEADERS   = "copyOfHeaders";
    /**
     * 不带-的uuid长度
     */
    public static final int                                            SHORT_UUID_LENGTH = 32;

    public static String                                               localHostName;

    private static String                                              applicationName;

    /**
     * 完成父线程到子线程的值传递
     */
    private static final TransmittableThreadLocal<Map<String, String>> MDC_COPY          = new TransmittableThreadLocal<>();

    static {
        try {
            localHostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.error("获取本机HostName异常:{}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
//        addRemoteEndpoint("");
//        addRemoteName("");
        Date date1 = new Date(1675699201000L);
        System.out.println(date1.toString());

        Date date2 = new Date(1676390399000L);
        System.out.println(date2.toString());

        // 创建  Inventor 对象
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
        // 1 定义解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 指定表达式
        Expression exp = parser.parseExpression("birthdate");
        // 在 tesla对象上解析
        String name = (String) exp.getValue(tesla);
        System.out.println(name); // Nikola Tesla

        exp = parser.parseExpression("name == 'Nikola Tesla'");
        // 在 tesla对象上解析并指定返回结果
        boolean result = exp.getValue(tesla, Boolean.class);
        System.out.println(result); // true
    }

    @Value("${spring.application.name}")
    public void setApplicationName(String appName) {
        applicationName = appName;
    }

    /*    */

    /**
     *  初始化mdc, 通常用于请求开始前
     * @param objects
     */
    /*
     * public static void initMDC(Printable obj) { if (obj == null) { return; }
     * String reqKey = getReqKey(); if (StringUtils.isNotBlank(reqKey)) { if
     * (StringUtils.isNotBlank(obj.buildMDCKey())) { MDC.put(REQ_KEY, reqKey +
     * obj.buildMDCKey()); } } else { MDC.put(REQ_KEY, obj.buildMDCKey()); }
     * addLocalHostName(); }
     */
    public static void initReqKey(Object... objects) {
        if (objects == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (Object item : objects) {
            builder.append(item);
        }
        String reqKey = getReqKey();
        Map<String, String> reqKeyMap = new HashMap<>(8);
        if (StringUtils.isNotBlank(reqKey)) {
            MDC.put(REQ_KEY, reqKey + builder.toString());
            reqKeyMap.put(REQ_KEY, reqKey + builder.toString());
        } else {
            MDC.put(REQ_KEY, builder.toString());
            reqKeyMap.put(REQ_KEY, builder.toString());
        }
        MDC_COPY.set(reqKeyMap);
        MDC.put(APPLICATION_NAME, applicationName);
        addLocalHostName();
    }

    public static void addLocalHostName() {
        String mdc = getReqKey();
        Map<String, String> reqKeyMap = MDC_COPY.get();
        if (StringUtils.isNotBlank(localHostName) && (mdc.indexOf(localHostName) < 0)) {
            if (StringUtils.isNotBlank(mdc)) {
                MDC.put(REQ_KEY, mdc + "-" + localHostName + "-");
                reqKeyMap.put(REQ_KEY, mdc + "-" + localHostName + "-");
            } else {
                MDC.put(REQ_KEY, localHostName + "-");
                reqKeyMap.put(REQ_KEY, localHostName + "-");
            }
        }
    }

    /**
     * 设置客户端ip
     *
     * @param ip 客户端ip
     */
    public static void addClientIp(String ip) {
        MDC.put(IP, ip);
        MDC_COPY.get().put(IP, ip);
    }

    /**
     * 设置remoteName
     *
     * @param remoteName
     */
    public static void addRemoteName(String remoteName) {

        try {
            remoteName = URLDecoder.decode(remoteName, "utf-8");
        } catch (UnsupportedEncodingException e) {

            log.info("编码失败,信息丢失,remoteName:{}", remoteName, e);
        }

        MDC.put(REMOTE_NAME, remoteName);
        MDC_COPY.get().put(REMOTE_NAME, remoteName);
    }

    /**
     * 设置remoteEndpoint
     *
     * @param remoteEndpoint
     */
    public static void addRemoteEndpoint(String remoteEndpoint) {

        try {
            remoteEndpoint = URLDecoder.decode(remoteEndpoint, "utf-8");
        } catch (UnsupportedEncodingException e) {

            log.info("编码失败,信息丢失,remoteEndpoint:{}", remoteEndpoint, e);
        }

        MDC.put(REMOTE_ENDPOINT, remoteEndpoint);
        MDC_COPY.get().put(REMOTE_ENDPOINT, remoteEndpoint);
    }

    /**
     * 获取reqKey
     *
     * @return
     */
    public static String getReqKey() {
        return getByKey(REQ_KEY);
    }

    /**
     * 获取远程appId应用名称 + 当前应用名称
     *
     * @return remoteName
     */
    public static String getRemoteName() {
        String remoteName = getByKey(REMOTE_NAME);
        if (StringUtils.isBlank(remoteName)) {
            return applicationName;
        } else {
            return remoteName + "," + applicationName;
        }
    }

    /**
     * 获取远程机器名称 + 当前机器名称
     *
     * @return remoteEndpoint
     */
    public static String getRemoteEndpoint() {

        String remoteEndpoint = getByKey(REMOTE_ENDPOINT);
        if (StringUtils.isBlank(remoteEndpoint)) {
            return localHostName;
        } else {
            return remoteEndpoint + "," + localHostName;
        }
    }

    /**
     * 获取客户端ip
     *
     * @return ip
     */
    public static String getClientIp() {
        return getByKey(IP);
    }

    /**
     * 根据key获取MDC中的值
     *
     * @param key key
     * @return MDC中的值, 可能返回<code>null</code>
     */
    private static String getByKey(String key) {
        String reqKey = MDC.get(key);
        if (StringUtils.isNotBlank(reqKey)) {
            return reqKey;
        } else if (null != MDC_COPY.get()) {
            return MDC_COPY.get().get(key);
        }
        return null;
    }

    public static void clear() {
        MDC.clear();
        MDC_COPY.remove();
    }
    @Override
    public void close() throws Exception {
        clear();
    }
}
