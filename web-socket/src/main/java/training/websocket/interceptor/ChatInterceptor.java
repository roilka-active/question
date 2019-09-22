package training.websocket.interceptor;/**
 * Package: training.websocket.interceptor
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/6 15:50
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description 聊天截断
 * @date 2019/8/6
 */
public interface ChatInterceptor {
    void checkMessage();
    int saveMessage();
}
