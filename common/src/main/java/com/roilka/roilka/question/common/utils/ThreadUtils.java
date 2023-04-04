package com.roilka.roilka.question.common.utils;

/**
 * @program: active-plat
 * @description: 线程工具类
 * @author: zhanghui
 * @date: 2023-03-19 16:35
 **/
public class ThreadUtils {

    /**
     *  线程等待
     * @param milliseconds
     */
    public static void sleep(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            return;
        }
    }
}
