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

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhanghui
 * @description 长连接控制类
 * @date 2019/7/24
 */
@Controller

public class WebSocketController {

    @GetMapping("/usercounter/socket/{cid}")
    public String socket(Model model, @PathVariable("cid") String cid){
        model.addAttribute("cid",cid);
        return "mav";
    }

    @SendTo("socketTo")
    public  String sendTo(){
        return "sd";
    }

}
