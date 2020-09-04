package com.roilka.roilka.question.common.javabase.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @ClassName NewThread
 * @Description 创建一个线程
 * @Author 75six
 * @Date 2020/3/20 16:12
 **/
public class NewThread {
    public static int a = 0;

    public static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //newThread();

        int taskSize = 10;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        List<Future> list = new ArrayList<Future>();
        Mycallable mycallable = new Mycallable();
        for (int i = 1; i <= 5; i++) {
            mycallable = new Mycallable();
            Future future = pool.submit(mycallable);

            list.add(future);
        }

        pool.shutdown();
        for (Future future : list) {
            System.out.println("res:" + future.get().toString());
        }
        FutureTask<Integer> futureTask = new FutureTask<>(mycallable);
        new Thread(futureTask).start();

        Callable callable = () -> {
            IntStream.rangeClosed(0, 10).forEach(i -> {
                System.out.println("aaa");
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId() + "  " + i);
            });

            return a;
        };
        futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        // get方法会阻塞调用的线程
        Integer sum = futureTask.get();
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId() + "=" + sum);

        System.out.println(a);
    }

    public static void newThread() throws InterruptedException {
        Thread thread = new MyThread();
        Thread thread1 = new MyThread1();
        thread.start();
        Thread.sleep(1000);
        thread1.start();
        Thread.interrupted();
        System.out.println(thread.getName());


    }


    public static void newRunnable() {
        new Thread(() -> {
            System.out.println("当前是 Thread-2");
            IntStream.range(0, 10).forEach(i ->{
                a++;
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId() + "  " + i);
            });


        }).start();
    }

    static class MyThread extends Thread {
        @Override
        public void run() {

            try {
                sleep(10000);
                System.out.println("睡眠时间结束");
            } catch (InterruptedException e) {
                System.out.println("当前被中断");
            }
            System.out.println("当前是 Thread-1");
            for (int i = 0; i < 15; i++) {
                a++;
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId() + "  " + i);
            }

        }

    }


    static class Mycallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            for (int i = 0; i < 15; i++) {
                a++;
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId() + "  " + i);
            }
            return a;
        }
    }

    static class MyThread1 extends Thread {
        @Override
        public void run() {
            System.out.println("当前是 Thread-3");
            for (int i = 0; i < 15; i++) {
                a++;
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId() + "  " + i);
            }
        }

    }

    public void addThread() {
        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId());
            }
        }).start();

        // 尾部代码块, 是对匿名内部类形式的语法糖
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId());
            }
        }.start();

        // Runnable是函数式接口，所以可以使用Lamda表达式形式
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "\t" + Thread.currentThread().getId());
        };
        new Thread(runnable).start();
    }
}
