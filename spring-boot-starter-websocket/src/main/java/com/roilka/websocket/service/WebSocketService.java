package com.roilka.websocket.service;/**
 * Package: com.roilka.websocket.service
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/6 20:30
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.websocket.entity.WebSocketBaseRequest;
import com.roilka.websocket.entity.WebSocketUserRequest;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;

/**
 * @author zhanghui
 * @description websocket服务
 * @date 2019/8/6
 */
public interface WebSocketService {

    JSONObject pushData(WebSocketBaseRequest request) throws IOException;
}
