package training.tools.algorithm;

public class ObjectTest {
    public static void main(String[] args) {
        Employee x = new Employee(2L);
        Employee y = new Employee(3L);
        swap(x, y);
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
