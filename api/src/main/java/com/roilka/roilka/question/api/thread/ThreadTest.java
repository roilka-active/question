package com.roilka.roilka.question.api.thread;/**
 * Package: com.roilka.roilka.question.api.thread
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/2/25 15:52
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description 线程测试
 * @date 2020/2/25
 */
public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new DeamonRunner(),"DeamonRunner");
        thread.setDaemon(true);
        thread.start();
        Thread.yield();
    }

}


