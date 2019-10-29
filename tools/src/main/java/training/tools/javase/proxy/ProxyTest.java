package training.tools.javase.proxy;

import training.tools.javase.Employee;
import training.tools.utils.SysTools;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        Object value = Employee.class;
        InvocationHandler invocationHandler = new TraceHandler(value);
        Class[] interfaces = new Class[]{Comparable.class};
        Object proxy = Proxy.newProxyInstance(null, interfaces,invocationHandler);
        Class proxyClass = Proxy.getProxyClass(null, interfaces);
        SysTools._out(proxyClass,"代理反转类：");
        SysTools._out(proxy.toString(), "代理的类是：");
    }
}
