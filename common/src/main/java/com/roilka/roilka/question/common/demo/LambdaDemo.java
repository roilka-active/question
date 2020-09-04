package com.roilka.roilka.question.common.demo;

import com.roilka.roilka.question.common.entity.Employee;

import java.util.ArrayList;

/**
 * @ClassName LambdaDemo
 * @Description TODO
 * @Author 75six
 * @Date 2020/5/18 10:19
 **/
public class LambdaDemo {
    public static void main(String[] args) {
        ArrayList<Employee> arrayList = new ArrayList<>();
        arrayList.add(new Employee(2.1));
        arrayList.add(new Employee(2.2));
        arrayList.add(new Employee(2.3));
        arrayList.add(new Employee(2.4));
        arrayList.stream().forEach(employee -> employee.setSalary(employee.getSalary() * 2));
        System.out.println(arrayList.toString());
    }
}
