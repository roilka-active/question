package com.roilka.roilka.question.common.collection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LinkedListDemo
 * @Description TODO
 * @Author 75six
 * @Date 2020/4/26 11:24
 **/
public class LinkedListDemo {
    public static volatile int count = 0;
    public static void main(String[] args) {

        List<Integer> list1 = Collections.synchronizedList(new LinkedList<>());


        buildThread(list1);
        System.out.println(list1);
        List<Integer> list = Collections.synchronizedList(new LinkedList<>());
        list.add(9);
    }
    public static void buildThread(List<Integer> list){

        for (int i = 0; i < 10; i++) {
            new Thread(()->{

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(count++);
                System.out.println(list);
            }).start();
        }
    }
}
