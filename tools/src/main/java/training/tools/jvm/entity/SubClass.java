package training.tools.jvm.entity;

import training.tools.utils.SysTools;

public class SubClass extends SuperClass implements SuperIntr3{


    static {
        SysTools._out("SubClass init!");

    }
    public static int value =23;
}
