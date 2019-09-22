package com.roilka.websocket.service;/**
 * Package: com.roilka.websocket.service
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/8 13:24
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.websocket.entity.WebSocketResponse;

/**
 * @author zhanghui
 * @description
 * @date 2019/8/8
 */
public interface WebsocketIntereptor {
    void before();
    void after(WebSocketResponse response);
}
