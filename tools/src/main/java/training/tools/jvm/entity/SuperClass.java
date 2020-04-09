package training.tools.jvm.entity;

import training.tools.utils.SysTools;

public class SuperClass implements SuperIntr2{
    static {
        SysTools._out("SuperClass static init!");
    }
    {
        SysTools._out("SuperClass init!");
    }
    public static int value = 123;
}
