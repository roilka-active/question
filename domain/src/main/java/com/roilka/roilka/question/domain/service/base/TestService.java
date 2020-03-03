package com.roilka.roilka.question.domain.service.base;

/**
 * @ClassName TestService
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/3/3 14:59
 **/
public interface TestService {
    default void say(){
        System.out.println("sdsd");
    }
}
