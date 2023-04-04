package com.roilka.roilka.question.common.javabase.concurrency;

import com.roilka.roilka.question.common.utils.ThreadUtils;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

/**
 * @program: active-plat
 * @description: 时间滑动窗口
 * @author: zhanghui
 * @date: 2023-03-19 16:16
 **/
public class TimeWindow {

    private ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();

    /**
     * 间隔秒数
     */
    private int seconds;

    /**
     * 最大限流
     */
    private int max;

    public TimeWindow(int seconds, int max) {
        this.seconds = seconds;
        this.max = max;

        new Thread(() -> {
            while (true) {
                ThreadUtils.sleep((seconds - 1) * 1000L);
                clean();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        final TimeWindow timeWindow = new TimeWindow(10, 5);

        IntStream.range(0,3).forEach(i -> {
            new Thread(()-> {
                while (true){
                    ThreadUtils.sleep(new Random().nextInt(20)*100);
                    timeWindow.take();
                }
            }).start();
        });
    }

    /**
     *  获取令牌，并且添加时间
     */
    public void take() {
        long start = System.currentTimeMillis();

        int size = sizeOfValid();

        if (size > max) {
            System.err.println("超限");
        }

        synchronized (queue){
            if (sizeOfValid() > max){
                System.err.println("超限");
                System.err.println("queue中有 " + queue.size() + " 最大数量 " + max);
            }
            this.queue.offer(System.currentTimeMillis());
        }

        System.out.println("queue中有 " + queue.size() + " 最大数量 " + max);
    }

    public int sizeOfValid() {
        Iterator<Long> iterator = queue.iterator();
        long l = System.currentTimeMillis() - seconds * 1000;
        int count = 0;
        while (iterator.hasNext()) {
            Long next = iterator.next();
            if (next > l) {
                // 在当前统计时间范围内
                count++;
            }
        }
        return count;
    }

    public void clean() {
        long l = System.currentTimeMillis() + seconds * 1000;

        Long tl = null;
        while ((tl = queue.peek()) != null && tl < l) {
            System.out.println("清理数据");
            queue.poll();
        }
    }
}
