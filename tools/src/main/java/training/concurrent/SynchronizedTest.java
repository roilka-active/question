package training.concurrent;/**
 * Package: training.concurrent
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 0:45
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanghui
 * @description Synchronized 学习
 * @date 2020/6/14
 */
public class SynchronizedTest {

    static Object object = new Object();
    public static void main(String[] args) {

        sync1();
        sync2();
    }

    public static void test01() {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println(ClassLayout.parseClass(Object.class).toPrintable());

        o.hashCode();

        synchronized (object) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        o.hashCode();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    public static void sync1() {

        new Thread(()->{
            synchronized (object) {
                System.out.println("running synnc1");
                sync2();
            }
        }).start();


    }

    public static void sync2() {
        Object o = new Object();

        new Thread(()->{
            synchronized (o) {
                System.out.println("running synnc2");
            }
        }).start();

    }
}
