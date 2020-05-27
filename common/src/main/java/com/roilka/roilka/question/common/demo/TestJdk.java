package com.roilka.roilka.question.common.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName TestJdk
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/21 10:48
 **/
public class TestJdk implements TestJava8,TestJdk8A {
    public static void main(String[] args) {
        TestJdk jdk = new TestJdk();
        jdk.sqrt(10);
        int a = 8;
        int b = 9;
        System.out.println(a & b);
        List<String> list = new ArrayList<>();
        list.add("aabc");
        list.add("bbac");
        list.add("babc");


    }

    @Override
    public double sqrt(int a) {
        return 0;
    }
}
