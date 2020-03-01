package com.roilka.roilka.question.api.thread;/**
 * Package: com.roilka.roilka.question.api.thread
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/2/25 16:18
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author zhanghui
 * @description 多线程
 * @date 2020/2/25
 */
public class MutiThread {

    public static void main(String[] args) {
        // 获取java 线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
        for (int i = 0; i < threadInfos.length; i++) {
            System.out.println("["+threadInfos[i].getThreadId()+"] "+ threadInfos[i].getThreadName() );
        }
    }
}
