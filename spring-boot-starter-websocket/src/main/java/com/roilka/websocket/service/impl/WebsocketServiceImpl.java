package com.roilka.websocket.service.impl;/**
 * Package: com.roilka.websocket.service.impl
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/8 13:21
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.websocket.HttpClientUtil;
import com.roilka.websocket.entity.WebSocketBaseRequest;
import com.roilka.websocket.entity.WebSocketUserRequest;
import com.roilka.websocket.service.WebSocketService;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghui
 * @description
 * @date 2019/8/8
 */
public class WebsocketServiceImpl implements WebSocketService {

    private String url;
    public WebsocketServiceImpl(String url){
        this.url = url;
    }
    @Override
    public JSONObject pushData(WebSocketBaseRequest request) throws IOException {
        Map<String,Object> body = new HashMap<>(2);
        body.put("socketIds",request.getSocketIds());
        body.put("data",request.getData());
        HttpClientUtil.doPostCommon(url,null,body,"utf-8");
        return null;
    }
}
