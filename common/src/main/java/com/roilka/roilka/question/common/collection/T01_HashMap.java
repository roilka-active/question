package com.roilka.roilka.question.common.collection;

import com.roilka.roilka.question.common.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName T01_HashMap
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/6/15 10:24
 **/
public class T01_HashMap {

    public static void main(String[] args) {
        MyThread myThread = null;
        /*for (int i = 0; i < 5; i++) {
             myThread = new MyThread();
            myThread.start();

        }*/
        test01();

    }

    public static void  test01(){
        HashMap<Object, Object> map = new HashMap<>(4);
        long start = System.currentTimeMillis();

        Set<Integer> hashSet = new HashSet<>();
        HashSet<Integer> objects = new HashSet<>();
        int j = 0;
        for (int i = 0; i < 1000000; i++) {
            String s = StringUtils.generateString(10);

            if (hashSet.add(s.hashCode()) && hashSet.size() < (i+1 -j)){
                j++;
                objects.add(i);
            }
            map.put(s, i*2);
        }
        System.out.println(objects);
        map.get(133);
        System.out.println("耗时："+(System.currentTimeMillis() - start) +"ms");
        int a = 8;
        System.out.println(a>>>1);
    }
}

class MyThread extends Thread {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static HashMap<Integer, Integer> hashMap = new HashMap(5);

    @Override
    public void run(){
        while (atomicInteger.get()<1000000){
            hashMap.put(atomicInteger.get(), atomicInteger.get());
            atomicInteger.incrementAndGet();
        }
    }
}