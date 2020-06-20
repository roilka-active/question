package training.concurrent.thread;
/**
 * Package: training.concurrent.thread
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 20:58
 * <p>
 * Modified By:
 */

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanghui
 * @description 本地线程测试
 * @date 2020/6/14
 */
public class T01_ThreadLocal {
    public static void main(String[] args) {
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        objectThreadLocal.set(new Person());
        objectThreadLocal.remove();

        WeakHashMap<Object, Object> objectObjectWeakHashMap = new WeakHashMap<>();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(objectThreadLocal.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(objectThreadLocal.get());
        }).start();
    }
    static class Person{
        String name ="zhangsan";
    }
}
