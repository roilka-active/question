package com.roilka.roilka.question.common.javabase.concurrent;

import java.util.Date;

/**
 * @ClassName WaitNotifyTest
 * @Description 线程等待唤醒测试
 * @Author 75six
 * @Date 2020/3/23 10:37
 **/
public class WaitNotifyTest {

    public static void main(String[] args) throws Exception {
        WaitNotifyTest waitNotifyTest = new WaitNotifyTest();
        new Thread(() -> {
            try {
                waitNotifyTest.printFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                waitNotifyTest.printFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t睡觉2秒中，目的是让上面的线程先执行，即先执行wait()");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            waitNotifyTest.notifyPrint();
        }).start();
    }

    private synchronized void printFile() throws InterruptedException {
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t等待打印文件...");
        this.wait();
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t打印结束。。。");
    }

    private synchronized void notifyPrint() {
        this.notifyAll();
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t通知完成...");
    }

}
