package training.tools.jvm.entity;

import training.tools.utils.SysTools;

public class ConstClass {
    static {
        SysTools._out("ConstClass init!");
    }
    // final 修饰，会在编阶段通过传播优化，已经将常量“hello world”存储到NoInitialization 类的常量池中
    public static final String HELLOWORD = "hello world";
}
