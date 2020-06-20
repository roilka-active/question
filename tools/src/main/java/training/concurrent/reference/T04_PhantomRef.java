package training.concurrent.reference;
/**
 * Package: training.concurrent.reference
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 20:36
 * <p>
 * Modified By:
 */

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanghui
 * @description 虚引用
 *              管理堆外内存
 * @date 2020/6/14
 */
public class T04_PhantomRef {

    private static final List<Object> list = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();
    public static void main(String[] args) {
        PhantomReference<M> mPhantomReference = new PhantomReference<>(new M(), QUEUE);



        new Thread(()->{
            while (true){
                list.add(new byte[1024*1024]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(mPhantomReference.get());
            }
        }).start();
        Thread thread = new Thread(() -> {


            while (true) {
                Reference<? extends M> poll = QUEUE.poll();

                if (poll != null) {
                    System.out.println("虚引用被JVM回收了。。。。。");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
