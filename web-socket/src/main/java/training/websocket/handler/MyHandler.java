package training.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.socket.*;
import training.tools.filter.FilterTest;
import training.websocket.entity.Message;
import training.websocket.entity.MessageKey;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;

public class MyHandler implements WebSocketHandler {

    private static final Map<String, Map<String, WebSocketSession>> sUserMap = new HashMap<>(3);

    private static volatile FilterTest filterTest = new FilterTest();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        String INFO = session.getUri().getPath().split("INFO=")[1];
        System.out.println(INFO);
        if (INFO != null && INFO.length() > 0) {
            String[] infoArr = INFO.split(",");
            //JSONObject jsonObject = JSONObject.parseObject(INFO);
            String command = infoArr[0];
            String roomId = infoArr[2];
            if (command != null && MessageKey.ENTER_COMMAND.equals(command)) {
                Map<String, WebSocketSession> mapSession = sUserMap.get(roomId);
                if (mapSession == null) {
                    mapSession = new HashMap<>(3);
                    sUserMap.put(roomId, mapSession);
                }
                mapSession.put(infoArr[1], session);
                session.sendMessage(new TextMessage("当前房间在线人数" + mapSession.size() + "人"));
                System.out.println(session);
            }
        }
        System.out.println("当前在线人数：" + sUserMap.size());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        try {
            JSONObject jsonobject = JSONObject.parseObject(webSocketMessage.getPayload().toString());
            Message message = new Message(jsonobject.toString());
            System.out.println(jsonobject.toString());
            System.out.println(message + ":来自" + webSocketSession.getAttributes().get(MessageKey.KEY_WEBSOCKET_USERNAME) + "的消息");
            if (message.getName() != null && message.getCommand() != null) {
                switch (message.getCommand()) {
                    case MessageKey.ENTER_COMMAND:
                        sendMessageToRoomUsers(message.getRoomId(), new TextMessage("【" + getNameFromSession(webSocketSession) + "】加入了房间，欢迎！"));
                        break;
                    case MessageKey.MESSAGE_COMMAND:
                        String content = message.getInfo();
                        content = filterTest.filterKeyWord(message.getInfo());
                        if (message.getName().equals("all")) {
                            sendMessageToRoomUsers(message.getRoomId(), new TextMessage(getNameFromSession(webSocketSession) +
                                    "说：" + message.getInfo()
                            ));
                        } else {
                            sendMessageToUser(message.getRoomId(), message.getName(), new TextMessage(getNameFromSession(webSocketSession) +
                                    "悄悄对你说：" + message.getInfo()));
                        }
                        break;
                    case MessageKey.LEAVE_COMMAND:
                        sendMessageToRoomUsers(message.getRoomId(), new TextMessage("【" + getNameFromSession(webSocketSession) + "】离开了房间，欢迎下次再来"));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送信息给指定用户
     *
     * @param name
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String roomId, String name, TextMessage message) {
        if (roomId == null || name == null) {
            return false;
        }
        if (sUserMap.get(roomId) == null) {
            return false;
        }
        WebSocketSession session = sUserMap.get(roomId).get(name);
        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     *
     * @param message
     * @return
     */
    public boolean sendMessageToRoomUsers(String roomId, TextMessage message) {
        if (roomId == null) {
            return false;
        }
        if (sUserMap.get(roomId) == null) {
            return false;
        }
        boolean allSendSuccess = true;
        Collection<WebSocketSession> sessions = sUserMap.get(roomId).values();
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }

    /**
     *  传输消息出错时触发的回调
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("连接出错");
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        Map<String, WebSocketSession> map = sUserMap.get(getRoomIdFromSession(webSocketSession));
        if (map != null) {
            map.remove(getNameFromSession(webSocketSession));
        }
    }

    private String filter(){

        try {
           return filterTest.filterKeyWord("fdsf张梅颖");
        }catch (Exception e){
            System.out.println("出错了");
        }
        return "";
    }
    /**
     *  断开连接后触发的回调
     * @param webSocketSession
     * @param closeStatus
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        System.out.println("连接已关闭：" + closeStatus);
        Map<String, WebSocketSession> map = sUserMap.get(getRoomIdFromSession(webSocketSession));
        if (map != null) {
            map.remove(getNameFromSession(webSocketSession));
        }
    }

    /**
     * 是否处理分片消息
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户名称
     *
     * @param session
     * @return
     */
    private String getNameFromSession(WebSocketSession session) {
        try {
            String name = (String) session.getAttributes().get(MessageKey.KEY_WEBSOCKET_USERNAME);
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取房间号
     *
     * @param session
     * @return
     */
    private String getRoomIdFromSession(WebSocketSession session) {
        try {
            String roomId = (String) session.getAttributes().get(MessageKey.KEY_ROOM_ID);
            return roomId;
        } catch (Exception e) {
            return null;
        }
    }

}
