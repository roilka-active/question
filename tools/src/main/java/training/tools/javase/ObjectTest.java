package training.tools.javase;

import training.tools.utils.SysTools;

import java.util.ArrayList;

public class ObjectTest {
    public static void main(String[] args) {
        Employee x = new Employee(2L);

        Employee y = new Employee(3L);
        Employee t = x.clone();
        x.setSalary(5D);
        SysTools._out(x,"当前员工");
        SysTools._out(t, "被拷贝的对象");

        swap(x, y);

        Manager z = new Manager(2, 4L);
        Double s = 23D;
        z.setSalary(23D);
        z.getCurrent();
        System.out.println("当前类是子类吗?" + (z.getCurrent() instanceof Manager));

        Object n = null;
        Object m = "章辉";
        System.out.println(z);

        ArrayList<Integer> arrayList = new ArrayList();

    }

    public static void say(Object... objs) {
        for (Object obj : objs) {
            System.out.println(obj);
        }
    }

    public static void swap(Employee a, Employee b) {
        Employee temp = a;
        a = b;
        b = temp;
    }

    /**
     * Employee employee = new Employee();
     * employee.setSalary(20L);
     * tripleSalary(employee);
     * System.out.println("employee`s salary is "+ employee.getSalary());
     *
     * @param temp
     */
    public static void tripleSalary(Employee temp) {
        temp.setSalary(temp.getSalary() * 3);
    }

    public static void mult(int a, int times) {
        a = a * times;
    }


}
