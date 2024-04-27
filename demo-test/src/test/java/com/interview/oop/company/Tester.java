package com.interview.oop.company;

import java.util.LinkedList;

import static java.lang.System.out;

public class Tester {
    public static void main(String[] args) {
        Employee employee1 = new Employee("John", 10000);
        Employee employee2 = new Employee("Mary", 20000);
        Employee employee3 = new Employee("John", 10000);

        out.println("Testing equals");
        out.println("employee1 == employee3 ? "
                + (employee1 == employee3));
        out.println("employee1.equals(employee3) ? "
                + employee1.equals(employee3));
        out.println("employee2.equals(employee3) ? "
                + employee2.equals(employee3));
        out.println();

        LinkedList<Employee> employees = new LinkedList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        out.println("Print using for each");
        for (Employee employee : employees) {
            out.println(employee);
        }
        out.println();

        out.println("Testing managers");
        Employee manager =
                new Manager("Tony", 100000, employees);
        employees.add(manager);
        for (Employee employee : employees) {
            out.println(employee);
        }
        out.println();

        out.println("Testing doWork");
        out.println("Expect exceptions because it tries to"
                + " load unmodifiable reporters.");
        manager.doWork();
    }
}
