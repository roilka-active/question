package training.tools.javase.iter;/**
 * Package: training.tools.javase.iter
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/10/11 0:18
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import training.tools.utils.SysTools;

import java.lang.reflect.Proxy;
import java.util.Vector;

/**
 * @author zhanghui
 * @description 静态内部类测试
 * @date 2019/10/11
 */
public class StaticInnerClassTest {

    public static void main(String[] args) {

        double[] d = new double[20];
        for (int i = 0; i < d.length; i++) {
            d[i] = 100 * Math.random();
        }


        ArrayAlg.Pair p = ArrayAlg.minmax(d);
        SysTools._out(p.getFirst(),"最大值：");
        SysTools._out(p.getSecond(),"最小值：");
    }
}
