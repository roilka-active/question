package com.roilka.websocket.entity;/**
 * Package: com.roilka.websocket.entity
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/6 20:46
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.io.Serializable;

/**
 * @author zhanghui
 * @description 用户请求对象
 * @date 2019/8/6
 */
public class WebSocketUserRequest implements Serializable {

    /**
     *  用户编号
     */
    private int userId;

    /**
     *  房间编号
     */
    private String module;

    public int getUserId() {
        return userId;
    }

    public WebSocketUserRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getModule() {
        return module;
    }

    public WebSocketUserRequest setModule(String module) {
        this.module = module;
        return this;
    }
}
