package training.spring.Bean;/**
 * Package: training.spring.Bean
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/29 1:02
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhanghui
 * @description
 * @date 2020/5/29
 */
@Component
public class Dog {
    public Dog(){
        System.out.println("dog constructor....");

    }

    @PostConstruct
    public void init(){
        System.out.println("dog init...@PostConstruct.");
    }

    @PreDestroy
    public void destory(){
        System.out.println("dog destory...@PreDestroy.");
    }
}
