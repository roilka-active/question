package com.roilka.roilka.question.common.demo;

/**
 * @ClassName TestJava8
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/21 10:28
 **/
public interface TestJava8 {

    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
