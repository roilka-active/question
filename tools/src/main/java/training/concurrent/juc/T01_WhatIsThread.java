package training.concurrent.juc;
/**
 * Package: training.concurrent.juc
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 22:59
 * <p>
 * Modified By:
 */

import java.util.concurrent.Callable;

/**
 * @author zhanghui
 * @description 什么是线程
 * @date 2020/6/14
 */
public class T01_WhatIsThread {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.interrupt();
        myThread.start();
        new Thread(new Myrun()).start();
        new Thread(()->{
            System.out.println("Hello lambda Thread!");
        }).start();

        Object o = new Object();

        o.notify();
    }

    static class MyThread extends Thread{
        @Override
        public void run(){
            System.out.println("Hello MyThread!");
        }
    }

    static class Myrun implements Runnable{

        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }
}
