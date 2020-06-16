package training.concurrent;/**
 * Package: training.concurrent
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/1/30 17:23
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanghui
 * @description 使用循环CAS实现原子操作
 * @date 2020/1/30
 */
public class CASDemo {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final CASDemo cas = new CASDemo();
        f();
    }
    public static void f(){
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "ds";
        b[0] = 32;
        new Thread(()->{
           System.out.println();
        });
    }
}
