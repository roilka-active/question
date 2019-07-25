package training.websocket.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import training.websocket.entity.MessageKey;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            String INFO = serverHttpRequest.getURI().getPath().split("INFO=")[1];
            if (INFO != null && INFO.length() > 0) {
                //JSONObject jsonObject = JSONObject.parseObject(INFO);
               // String command = jsonObject.getString("command");
                String[] infoArr = INFO.split(",");
                //JSONObject jsonObject = JSONObject.parseObject(INFO);
                String command = infoArr[0];
                String name = infoArr[1];
                String roomId = infoArr[2];
                if (command != null && MessageKey.ENTER_COMMAND.equals(command)) {
                    System.out.println("当前session的ID="+ name);
                    ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
                    HttpSession session = request.getServletRequest().getSession();
                    map.put(MessageKey.KEY_WEBSOCKET_USERNAME, name);
                    map.put(MessageKey.KEY_ROOM_ID, roomId);
                }
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("进来webSocket的afterHandshake拦截器！");
    }
}
