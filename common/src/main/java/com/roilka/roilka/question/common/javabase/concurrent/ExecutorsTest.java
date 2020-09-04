package com.roilka.roilka.question.common.javabase.concurrent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ExecutorsTest
 * @Description changyou测试
 * @Author 75six
 * @Date 2020/4/10 14:40
 **/
public class ExecutorsTest {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Class clazz = ExecutorsTest.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().contains("main")) {
                continue;
            }
            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                method.invoke(clazz);
            }

            method.invoke(clazz, 2);
        }
        new Thread(() -> {
            System.out.println("I`m who?");
        }).start();
    }

    /**
     * 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
     *
     * @param n
     */
    public void testNewFixedThreadPool(int n) {
        System.out.println("testNewFixedThreadPool I`m running...");
        executor(Executors.newFixedThreadPool(n));

    }

    /**
     * 这个线程
     * 池可以在线程死后（或发生异常时）重新启动一个线程来替代原来的线程继续执行下去
     */
    public void newSingleThreadExecutor() {
        System.out.println("newSingleThreadExecutor I`m running...");
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        this.executor(threadPool);

    }

    /**
     * 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
     *
     * @param n
     */
    public void newScheduledThreadPool(int n) {
        System.out.println("newScheduledThreadPool I`m running...");
        executor(Executors.newScheduledThreadPool(n));

    }

    /**
     * 调用 execute 将重用以前构造
     * 的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并
     * 从缓存中移除那些已有 60 秒钟未被使用的线程
     */
    public void newCachedThreadPool() {
        System.out.println("newCachedThreadPool I`m running...");
        executor(Executors.newCachedThreadPool());

    }

    private void executor(ExecutorService threadPool) {
        while (true) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "Time:" + System.currentTimeMillis() + " is running ..");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
