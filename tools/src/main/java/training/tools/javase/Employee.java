package training.tools.javase;

import java.util.Date;

public class Employee implements Cloneable{
    private  final Date date;
    private Date hireDay;
    private double salary;

    private static int nextCode;
    private int code;
    Employee() {
        this.date = new Date();
    }
    public Employee(double salary) {
        System.out.println("Employee is init!");
        this.salary = salary;
        this.date = new Date();
        this.hireDay = new Date();
    }
    {
        code = 1;
        nextCode ++;
        System.out.println("我是初始化块");
    }

    static {
        nextCode = 3;
        System.out.println("我是静态初始化块");
    }
    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void setDateTime(Long date) {
        this.date.setTime(date);
    }

    public Date getDate() {
        return date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        System.out.println("我是父类");
        this.salary = salary;
    }

    public Employee getCurrent(){
        return this;
    }
    @Override
    public String toString() {
        return getClass().getName()+"={" +
                "code=" + code +
                "salary=" + salary +
                "hireDay=" + hireDay +
                '}';
    }

    public Employee clone() {

        Employee cloned = null;
        try {
            cloned = (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
            //this won`t happen,since we are Cloneable
        }
        cloned.hireDay = (Date) hireDay.clone();
        return cloned;
    }
}
