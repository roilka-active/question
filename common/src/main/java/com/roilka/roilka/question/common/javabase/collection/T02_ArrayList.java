package com.roilka.roilka.question.common.javabase.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghui1
 * @ClassName T02_ArrayList
 * TODO
 * @date 2020/9/2 10:11
 **/
public class T02_ArrayList {
    public static void main(String[] args) {
        List<Object> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add(Math.random() * 100);
        }
        List<Object> arrayList2 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList2.add(Math.random() * 100);
        }
        System.out.println("集合1：" + arrayList);
        System.out.println("集合2" + arrayList2);

    }
}
