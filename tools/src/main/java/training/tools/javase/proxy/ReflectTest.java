package training.tools.javase.proxy;

import training.tools.javase.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName ReflectTest
 * @Description 反射测试类
 * @Author zhanghui1
 * @Date 2020/4/9 16:50
 **/
public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException {
        // 调用某个对象的getClass()方法
        Employee employee = new Employee(10);
        Class clazz = employee.getClass();

        // 调用某个类的class 属性来获取该类对应的Class 对象
        Class clazzA = Employee.class;

        // 使用 Class 类中的 forName()静态方法(最安全/性能最好) 同时也是最常用
        Class clazzB = Class.forName("training.tools.javase.Employee");

        // 获取Employee 类的所有方法信息
        Method[] methods = clazzB.getDeclaredMethods();

        for (Method m : methods){
            System.out.println(m.toString());
        }

        // 获取Employee 类的所有成员属性信息
        Field[] fields = clazzB.getDeclaredFields();
        for (Field f : fields){
            System.out.println(f.toString());
        }

        // 获取Employee 类的所有构造方法信息
        Constructor[] constructors = clazzB.getDeclaredConstructors();
        for (Constructor c : constructors){
            System.out.println(c.toString());
        }
    }
}
