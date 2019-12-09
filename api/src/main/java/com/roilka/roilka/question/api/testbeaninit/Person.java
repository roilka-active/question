package com.roilka.roilka.question.api.testbeaninit;

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
