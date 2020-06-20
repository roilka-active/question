package training.concurrent.reference;
/**
 * Package: training.concurrent.reference
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/14 20:33
 * <p>
 * Modified By:
 */

import java.lang.ref.WeakReference;

/**
 * @author zhanghui
 * @description 弱引用 一次性使用
 *                  一旦gc，肯定回收
 * @date 2020/6/14
 */
public class T03_WeekRef {

    public static void main(String[] args) {
        WeakReference m = new WeakReference<M>(new M());
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());
    }
}
