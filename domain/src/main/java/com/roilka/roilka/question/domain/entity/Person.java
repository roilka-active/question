package com.roilka.roilka.question.domain.entity;

import java.util.List;

/**
 * @author zhanghui
 * @description 人类接口
 * @date 2019/11/30
 */
public interface Person {

    void service(String s);

    void setAnimal(Animal animal);

    void setAnimalList(List<Animal> animalList);
}
