package com.roilka.roilka.question.common.javabase.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: active-plat
 * @description: 计数器
 * @author: zhanghui
 * @date: 2023-03-19 16:03
 **/
public class Counter {

    /**
     *  最大访问量
     */
    private final int limit = 10;

    /**
     *  访问时间差
     */
    private final long timeout = 1000;

    /**
     *  请求时间
     */
    private long time;

    private AtomicInteger reqCount = new AtomicInteger(0);

    public boolean limit(){
        long now = System.currentTimeMillis();
        if (now < time + timeout){
            // 单位时间内
            reqCount.addAndGet(1);
            return reqCount.get() < limit;
        }else {
            // 超出单位时间
            time = now;
            reqCount = new AtomicInteger(0);
            return true;
        }
    }
}
