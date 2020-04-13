package com.roilka.roilka.question.common.concurrent.entity;

/**
 * @ClassName HelloWoorld
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/13 18:51
 **/
public class HelloWoorld implements Hello{
    @Override
    public void morning(String name) {
        System.out.println("Good morning "+ name);
    }
}
