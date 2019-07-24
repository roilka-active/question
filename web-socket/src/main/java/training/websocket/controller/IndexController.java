package training.websocket.controller;/**
 * Package: training.websocket.controller
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/7/24 19:59
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhanghui
 * @description 初始化控制器
 * @date 2019/7/24
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
