package training.tools.algorithm;

import java.util.Arrays;

public class ObjectTest {
    public static void main(String[] args) {
        Employee x = new Employee(2L);
        Employee y = new Employee(3L);
        swap(x, y);

        Manager z = new Manager(2,4L);
        Double s = 23D;
        z.setSalary(23D);
        z.getCurrent();
        System.out.println("当前类是子类吗?"+ (z.getCurrent() instanceof  Manager));
        Object a = null;
        Object b = "章辉";
        System.out.println(b.hashCode());
        System.out.println(z);

    }

    public static void swap(Employee a, Employee b) {
        Employee temp = a;
        a = b;
        b = temp;
        Employee c;
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
