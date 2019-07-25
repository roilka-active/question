package training.websocket.server;/**
 * Package: training.websocket.server
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/7/24 18:26
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zhanghui
 * @description 服务端
 * @date 2019/7/24
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServerSet = new CopyOnWriteArraySet<>();
    /**
     * //与某个客户端的连接对话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * //接受sid
     */
    private String sid = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketServerSet.add(this);
        logger.info("有新窗口开始监听：" + sid + "，当前在线人数为" + getOnlineCount());
        addOnlineCount();
        this.sid = sid;

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketServerSet.remove(this);
        subOnlineCount();
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自窗口" + sid + "的信息：" + message);
        for (WebSocketServer item : webSocketServerSet) {
            try {
                item.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误", error);
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {

        this.session.getBasicRemote().sendText(message);
    }

    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        logger.info("推送消息到窗口" + sid + "，推送内容：" + message);
        for (WebSocketServer item : webSocketServerSet
        ) {
         try {
             if (sid==null){
                 item.sendMessage(message);
             }else if (item.sid.equals(sid)){
                 item.sendMessage(message);
             }
         }catch (IOException e){
             continue;
         }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        if (onlineCount > 0){
            WebSocketServer.onlineCount--;
        }

    }
}
