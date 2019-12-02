package com.roilka.roilka.question.api.testbeaninit;/**
 * Package: com.roilka.roilka.question.api.redis
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/11/30 11:01
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.util.List;

/**
 * @author zhanghui
 * @description 人类接口
 * @date 2019/11/30
 */
public interface Person {

    void service();

    void setAnimal(Animal animal);

    void setAnimalList(List<Animal> animalList);
}
