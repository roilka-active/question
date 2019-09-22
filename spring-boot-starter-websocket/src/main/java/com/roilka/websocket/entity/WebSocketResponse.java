package com.roilka.websocket.entity;/**
 * Package: com.roilka.websocket.entity
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/7 9:51
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
 * @description 统一返回对象
 * @date 2019/8/7
 */
public class WebSocketResponse implements Serializable {
    private static final long serialVersionUID = -2301709917165099461L;

    private int code;

    private String msg;
}
