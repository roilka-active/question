package com.roilka.roilka.question.common.javabase.concurrent;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName ThreadPool
 * @Description TODO
 * @Author 75six
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

    public void shutDown() {
        Collection collection = new ArrayList();
        Map map = new HashMap();
        NavigableMap<Integer, String> sortedMap = new TreeMap<>();
        sortedMap.put(2, "bb");
        sortedMap.put(1, "aa");
        sortedMap.put(4, "dd");
        sortedMap.put(3, "cc");
        System.out.println("sortedMap.floorEntry:"+sortedMap.floorEntry(2));
        System.out.println("sortedMap.ceilingEntry:"+sortedMap.ceilingEntry(2));
        System.out.println(sortedMap.computeIfAbsent(2,k -> "ws"));
        System.out.println(sortedMap.floorEntry(2));
    }

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("当前线程正在运行");
        }).start();
    }
}
