package training.tools.algorithm;

import java.math.BigDecimal;
import java.util.Date;

public class Employee {
    private final Date date;
    private Date hireDay;
    private double salary;
    Employee() {
        this.date = new Date();
    }
    Employee(double salary) {
        this.salary = salary;
        this.date = new Date();
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

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
