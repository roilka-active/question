package com.roilka.roilka.question.api.testbeaninit;/**
 * Package: com.roilka.roilka.question.api.testbeaninit
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/11/30 18:23
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhanghui
 * @description
 * @date 2019/11/30
 */
@Component
public class BussinessPerson implements Person {

    @Autowired
    private Animal animal = null;

    @Override
    public void service() {
        this.animal.use();
    }

    @Override
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
