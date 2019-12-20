package com.roilka.roilka.question.common.filter;

import com.roilka.roilka.question.common.utils.MDCUtils;
import com.roilka.roilka.question.common.utils.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName AbstractMDCFilter
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/6 20:50
 **/
public abstract class AbstractMDCFilter implements Filter {
    public static String mdcPrefix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) request;
            String traceNo = httpReq.getHeader(MDCUtils.TRACE_NO);
            String clientIp = httpReq.getHeader(MDCUtils.SC_CLIENT_IP);
            String remoteName = httpReq.getHeader(MDCUtils.REMOTE_NAME);
            if (StringUtils.isNotBlank(remoteName)) {
                MDC.put(MDCUtils.REMOTE_NAME, remoteName);
            }
            String remoteEndpoint = httpReq.getHeader(MDCUtils.REMOTE_ENDPOINT);
            if (StringUtils.isNotBlank(remoteEndpoint)) {
                MDC.put(MDCUtils.REMOTE_ENDPOINT, remoteEndpoint);
            }
            traceMDC(traceNo);
            if (StringUtils.isNotBlank(clientIp)) {
                MDCUtils.addClientIp(clientIp);
            }

            chain.doFilter(request, response);
        } finally {
            MDCUtils.clear();
        }
    }

    @Override
    public void destroy() {
    }

    public static void traceMDC(String traceNo) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(traceNo)) {
            builder.append(traceNo);
            builder.append("_");
        }
        if (StringUtils.isNotBlank(mdcPrefix)) {
            builder.append(mdcPrefix);
            builder.append("-");
        }
        String shortJavaUUID = shortJavaUUID();
        builder.append(shortJavaUUID);

        MDCUtils.initReqKey(builder.toString());
        MDC.put("shortJavaUUID", shortJavaUUID);
    }

    public String getMdcPrefix() {
        return mdcPrefix;
    }

    public void setMdcPrefix(String mdcPrefix) {
        if (StringUtils.isNotBlank(mdcPrefix)) {
            this.mdcPrefix = mdcPrefix;
        }
    }

    // 同一时间点有重复，基于时间
    public String shortUUID() {
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String time = sdf.format(date);
        for (int i = 0; i < time.length() / 2; i++) {
            String singleChar;
            String x = time.substring(i * 2, (i + 1) * 2);
            int b = Integer.parseInt(x);
            if (b < 10) {
                singleChar = Integer.toHexString(Integer.parseInt(x));
            } else if (b >= 10 && b < 36) {
                singleChar = String.valueOf((char) (Integer.parseInt(x) + 55));
            } else {
                singleChar = String.valueOf((char) (Integer.parseInt(x) + 61));
            }
            builder.append(singleChar);
        }
        return builder.toString();
    }

    // 10000次内有五次左右重复，可接受
    public static String shortJavaUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }


}
