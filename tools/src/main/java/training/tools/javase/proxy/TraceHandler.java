package training.tools.javase.proxy;

import training.tools.utils.SysTools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TraceHandler implements InvocationHandler {

    private Object target;

    public TraceHandler(Object t){
        target = t;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //print implicit argument
        SysTools._out(target, "target:");
        //print method name
        SysTools._out(method.getName(), ".","(");
        //print explicit arguments
        if (args != null){
            for (int i = 0; i < args.length; i++) {
                SysTools._out(args[i]);
                if (i < args.length){
                    SysTools._out(",");
                }
            }
        }
        return method.invoke(target, args);
    }
}
