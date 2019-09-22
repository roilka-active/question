package training.websocket.controller;/**
 * Package: training.websocket.controller
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/7/24 19:18
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.websocket.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import training.tools.filter.FilterTest;
import training.websocket.server.WebSocketServer;

/**
 * @author zhanghui
 * @description 长连接控制类
 * @date 2019/7/24
 */
@Controller

public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private ExampleService  exampleService;
    @GetMapping("/usercounter/socket/{cid}")
    public String socket(Model model, @PathVariable("cid") String cid){
        model.addAttribute("cid",cid);
        FilterTest filterTest = new FilterTest();
        try {
            filterTest.filterKeyWord("fdsf张梅颖");
        }catch (Exception e){
            System.out.println("出错了");
        }

        return "mav";
    }
    @GetMapping("/send/{val}")
    public  String send(@PathVariable("val") String val){
        exampleService.wrap("反倒是第三方");
        return "";
    }
    @SendTo("socketTo")
    public  String sendTo(){
        return "sd";
    }

}
