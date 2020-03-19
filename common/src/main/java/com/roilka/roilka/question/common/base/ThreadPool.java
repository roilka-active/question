package com.roilka.roilka.question.common.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName ThreadPool
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/3/19 19:45
 **/
public class ThreadPool {

    // 核心线程数量
    private volatile int corePoolSize;

    // 最大线程数
    private volatile int maximumPoolSize;
    // 工作队列
    private final BlockingQueue<Runnable> workQueue;
    // 队列等待时间
    private volatile long keepAliveTime;
    // 线程工厂
    private volatile ThreadFactory threadFactory;

    // 拒绝策略
    private volatile RejectedExecutionHandler handler;

    ThreadPool(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("当前线程正在运行");
        }).start();
    }
}
