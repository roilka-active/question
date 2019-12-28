package com.roilka.roilka.question.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roilka.roilka.question.common.base.BizRestException;
import com.roilka.roilka.question.common.enums.BizResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName HttpClientUtils
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/26 18:04
 **/
@Slf4j
@Configuration
public class HttpClientUtils {

    @Autowired
    private CloseableHttpClient httpClient;
    public JSONObject doBasePost(String url,Map<String,Object> header,Map<String,Object> body,String encode){

        return null;
    }

    public JSONObject doGetWithParam(String urlTo, Map<String, Object> map){
        return this.doGetWithParam(urlTo, map, "UTF-8");
    }
    public JSONObject doGetWithParam(String urlTo, Map<String, Object> map,String charSet){
        StringBuffer url = new StringBuffer(urlTo);

        if (!map.isEmpty()) {
            url.append("?");
            int i = 0;
            //拼接url参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (i == 0) {
                    i++;
                } else {
                    url.append("&");
                }
                url.append(entry.getKey()).append("=").append(entry.getValue());

            }
        }
        return this.doGetBase(url.toString(),null, charSet);
    }
    /**
     * get请求
     *
     * @param url url
     * @param charset 字符集
     * @return String
     */
    public  JSONObject doGetBase(String url,Map<String,String> header, String charset) {

        CloseableHttpResponse response = null;
        String result = null;
        long startTime = -1;
        try {

            HttpGet httpGet = new HttpGet(url);
            if (header != null) {
                Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> elem = iterator.next();
                    httpGet.addHeader(elem.getKey(), elem.getValue());
                }
            }
            log.info("GET请求开始调用,URL={}", url);
            startTime = System.currentTimeMillis();
            // 执行http请求
            response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), charset);
            return JSONObject.parseObject(result);

        } catch (Exception ex) {
            log.error("服务调用异常,URL={}", url, ex);
            throw new BizRestException(BizResponseCodeEnum.CALLSERVICCE_ERROR, "服务调用异常");
        } finally {
            long timeToken = (-1 == startTime) ? -1 : System.currentTimeMillis() - startTime;
            log.info("GET请求结束调用,URL={},耗时={}ms,result={}", url, timeToken, result);
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("关闭http连接异常,URL={}", url, e);
            }
        }
    }

}
