package com.roilka.roilka.question.common.javabase.concurrency;

/**
 * @author zhanghui1
 * @ClassName Test01_Concurrent
 * TODO
 * @date 2020/8/31 14:41
 **/
public class Test01_Concurrent {

    public synchronized String lockA(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I`m lock A");
        return "lockA";
    }
    public static synchronized String lockB(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I`m lock B");
        return "lockB";
    }
}
