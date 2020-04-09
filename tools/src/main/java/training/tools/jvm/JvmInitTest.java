package training.tools.jvm;

import training.tools.jvm.entity.ConstClass;
import training.tools.jvm.entity.Object;
import training.tools.jvm.entity.SubClass;
import training.tools.jvm.entity.SuperClass;
import training.tools.utils.SysTools;

public class JvmInitTest {
    public static void main(String[] args) {

        SysTools._out(SubClass.value);
        //对象数组只是虚拟机自动生成的、直接继承Object的子类，创建动作由字节码指令new array触发
        //SuperClass[] arr = new SuperClass[10];
        SysTools._out(ConstClass.HELLOWORD);
        SysTools._out(Object.a);
    }
}
