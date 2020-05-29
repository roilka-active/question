package com.roilka.roilka.question.common.config;

import org.springframework.context.support.GenericApplicationContext;

/**
 * @ClassName GetBeanCustom
 * @Description TODO
 * @Author 75six
 * @Date 2020/4/16 11:06
 **/
public class GetBeanCustom {
    public static void main(String[] args) {
        GenericApplicationContext ac = new GenericApplicationContext();
        Object o = new Object();
        o.hashCode();
        o.equals(null);
    }
}
