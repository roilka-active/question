package training.websocket.config;/**
 * Package: training.tools.filter
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/8/2 15:26
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.tools.jar.CommandLine;
import training.tools.filter.FilterRunner;
import training.tools.filter.SensitiveFilter;

/**
 * @author zhanghui
 * @description
 * @date 2019/8/2
 */
@Component
@Order(2)
public class FilterConfig implements CommandLineRunner {

    @Override
    public void run(String[] args) throws Exception {
        iniFilterCinfig();
    }
    public void iniFilterCinfig(){
        System.out.println("项目又启动了，这次使用的是：继承 ApplicationRunner");
        SensitiveFilter.init();
    }
}
