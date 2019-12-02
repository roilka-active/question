package com.roilka.roilka.question.api.testbeaninit;/**
 * Package: com.roilka.roilka.question.api.testbeaninit
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/11/30 19:11
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhanghui
 * @description 狗
 * @date 2019/11/30
 */
@Slf4j
@Component
public class Dog implements Animal {
    @Override
    public void use() {
        log.info("狗{} 是看门用的", Dog.class.getSimpleName());
    }
}
