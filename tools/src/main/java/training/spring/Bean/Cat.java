package training.spring.Bean;/**
 * Package: training.spring.Bean
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/29 0:44
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author zhanghui
 * @description
 * @date 2020/5/29
 */

@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat(){
        System.out.println("cat ... constructor ...");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("cat ... destroy ...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat ... afterPropertiesSet ...");
    }
}
