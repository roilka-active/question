package com.roilka.roilka.question.api.thread;/**
 * Package: com.roilka.roilka.question.api.thread
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/2/25 15:57
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
 * @description Deamon线程
 * @date 2020/2/25
 */
public class DeamonRunner implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("Deamon sleep");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Deamon start");
        } catch (InterruptedException e) {
            System.out.println("Deamon start exception");
        }finally {
            System.out.println("Deamon start finish");
        }
    }
}
