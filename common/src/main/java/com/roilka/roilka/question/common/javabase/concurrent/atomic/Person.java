package com.roilka.roilka.question.common.javabase.concurrent.atomic;

import lombok.Data;

/**
 * @ClassName Person
 * @Description TODO
 * @Author 75six
 * @Date 2020/4/22 18:57
 **/
@Data
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
