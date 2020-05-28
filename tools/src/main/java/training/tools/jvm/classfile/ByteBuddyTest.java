package training.tools.jvm.classfile;/**
 * Package: training.tools.jvm.classfile
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/1/9 0:57
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;

/**
 * @author zhanghui
 * @description ByteBuddy 测试
 * @date 2020/1/9
 */
public class ByteBuddyTest {
    public static void main(String[] args) {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .make();
    }
}
