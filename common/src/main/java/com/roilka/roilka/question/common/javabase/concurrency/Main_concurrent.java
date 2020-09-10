package com.roilka.roilka.question.common.javabase.concurrency;

/**
 * @author zhanghui1
 * @ClassName Main_concurrent
 * TODO
 * @date 2020/8/31 14:44
 **/
public class Main_concurrent {

    public static void main(String[] args) {
//        Test01_Concurrent test01Concurrent = new Test01_Concurrent();
//        Thread thread = new Thread(() -> test01Concurrent.lockA());
//        Thread thread1 = new Thread(() -> Test01_Concurrent.lockB());
//        thread.start();
//        thread1.start();

        Object o = new Object();
        synchronized (o){
            o = null;
        }

    }
}
