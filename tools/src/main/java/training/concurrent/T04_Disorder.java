package training.concurrent;/**
 * Package: training.concurrent
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 16:53
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description cpu乱排序
 * @date 2020/6/14
 */
public class T04_Disorder {

    private static int a = 0, b = 0;
    private static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;y=0;
            a=0;b=0;
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });
            Thread two = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();two.start();
            one.join();two.join();
            String result = "第"+i+"次 （ x ="+x+",y = "+y+")";
            if (x == 0 && y == 0){
                System.err.println(result);
            }
        }
    }
}
