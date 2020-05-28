package com.roilka.websocket.entity;/**
 * Package: com.roilka.websocket.entity
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/6 20:38
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.io.Serializable;
import java.util.List;

/**
 * @author zhanghui
 * @description 基础请求实体
 * @date 2019/8/6
 */
public class WebSocketBaseRequest<T> implements Serializable {

    private List<WebSocketUserRequest> socketIds;

    private T data;

    public List<WebSocketUserRequest> getSocketIds() {
        return socketIds;
    }

    public WebSocketBaseRequest<T> setSocketIds(List<WebSocketUserRequest> socketIds) {
        this.socketIds = socketIds;
        return this;
    }

    public T getData() {
        return data;
    }

    public WebSocketBaseRequest<T> setData(T data) {
        this.data = data;
        return this;
    }
}
