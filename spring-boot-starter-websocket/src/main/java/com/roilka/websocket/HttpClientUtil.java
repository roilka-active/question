package com.roilka.websocket;/**
 * Package: com.roilka.websocket
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/7 10:52
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhanghui
 * @description
 * @date 2019/8/7
 */
public class HttpClientUtil {
    static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final RequestConfig CONFIG = RequestConfig.custom().setConnectTimeout(10 * 1000)
            .setConnectionRequestTimeout(10 * 10000)
            .setSocketTimeout(60 * 1000).build();
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom().setDefaultRequestConfig(CONFIG).build();

    public static JSONObject doPostCommon(String url, Map<String, String> header, Map<String, Object> body,
                                          String charset) throws IOException {
        HttpPost httpPost = null;
        JSONObject jsonResult = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            //如果你没有header,请将此参数设置为null
            if (header != null) {
                Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                    httpPost.addHeader(elem.getKey(), elem.getValue());
                }
            }
            //设置参数
            StringEntity sEntity = new StringEntity(JSON.toJSONString(body), charset);
            sEntity.setContentType("application/json");
            sEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(sEntity);
            response = HTTP_CLIENT.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String sResult = EntityUtils.toString(resEntity, charset);
                    if (sResult != null && sResult != "") {
                        jsonResult = JSON.parseObject(sResult);
                        return jsonResult;
                    }
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("关闭http连接异常,URL={}", url, e);
            }
        }
        return null;
    }
}
