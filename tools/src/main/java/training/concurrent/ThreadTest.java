package training.concurrent;/**
 * Package: training.concurrent
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/13 23:00
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanghui
 * @description
 * @date 2020/6/13
 */
public class ThreadTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10));

        for (int i = 0 ;i< 5 ;i++){
            int finalI = i;
            threadPoolExecutor.execute(()->{
                System.out.println("No1.当前我已经开始执行了。。。");
                try {
                    TimeUnit.MINUTES.sleep(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("No1.当前我执行结束了。。。");
            });
        }

        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndAdd(1);

        System.out.println(ClassLayout.parseInstance(atomicInteger).toPrintable());

        System.out.println(ClassLayout.parseClass(AtomicInteger.class).toPrintable());
    }
}
