package com.roilka.roilka.question.common.javabase.concurrency;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhanghui
 * @description 测试多线程
 * @date 2019/12/10
 */
public class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            CountTask left = new CountTask(start, middle);
            CountTask right = new CountTask(middle + 1, end);
            //执行子任务
            left.fork();
            right.fork();
            //等待子任务完成，并得到结果
            int leftR = left.join();
            int rightR = right.join();
            sum = leftR + rightR;
        }
        return sum;
    }

    public static void main(String[] args) {
        int size = 10000000;
        long end = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成一个计算任务，负责计算1+2+3+4
        CountTask task = new CountTask(1, size);

        //执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - end) + "毫秒");
        int sum = 0;
        end = System.currentTimeMillis();
        for (int i = 0; i <= size; i++) {
            sum += i;
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - end) + "毫秒");
    }
}
