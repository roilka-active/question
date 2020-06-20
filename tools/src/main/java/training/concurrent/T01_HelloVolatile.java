package training.concurrent;/**
 * Package: training.concurrent
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 14:49
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.util.concurrent.TimeUnit;

/**
 * @author zhanghui
 * @description 线程可见性
 * @date 2020/6/14
 */
public class T01_HelloVolatile {
    volatile boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {
            System.out.println("i am waiting");
        }
        System.out.println("m end!");

    }

    public static void main(String[] args) {
        T01_HelloVolatile t = new T01_HelloVolatile();
        new Thread(t::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        t.running = false;
    }
}
