package com.roilka.roilka.question.common.demo;

/**
 * @ClassName TestJdk8A
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/21 10:53
 **/
public interface TestJdk8A {
    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
