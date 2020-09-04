package com.roilka.roilka.question.common.javabase.concurrent;

import java.util.Date;

/**
 * @ClassName JoinTest
 * @Description JoinTest 加入测试
 * @Author 75six
 * @Date 2020/3/23 11:04
 **/
public class JoinTest {
    public static void main(String[] args) throws Exception {
        new Thread(new ParentRunnable()).start();
    }

    static class ParentRunnable implements Runnable {

        @Override
        public void run() {
            // 线程处于new状态
            Thread childThread = new Thread(new ChildRunnable());
            // 线程处于runnable就绪状态
            childThread.start();
            try {
                // 当调用join时，parent会等待child执行完毕后再继续运行
                // 将某个线程加入到当前线程
                childThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "父线程 running");
            }

        }
    }

    static class ChildRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "子线程 running");
            }

        }
    }
}
