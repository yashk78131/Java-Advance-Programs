package streams;

/**
 * Program Title: Java Streams example with Real world scenari
 * Author: [hirushi1999]
 * Date: 2025-10-14
 * Demonstrates how to use Java Streams to perform data analysis on a list of employees.
 * Scenario:
 * - A company has employees working in various departments (HR, Engineering, Sales).
 * - Each employee has attributes such as age, salary, and active status.
 * Task:
 * 1. Count how many employees fall into each age group.
 * 2. Find the highest salary in each department among active employees.
 * 3. List all active employees.
 */

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalysis {
    public static void main(String[] args) {
        //Create list of employees
        List<Employee> employeeList = Arrays.asList(
                new Employee(1, "Alice", "HR", 25, 50000.0, true),
                new Employee(2, "Bob", "Engineering", 35, 70000.0, false),
                new Employee(3, "Charlie", "HR", 30, 55000.0, true),
                new Employee(4, "David", "Engineering", 28, 72000.0, true),
                new Employee(5, "Ella", "Sales", 26, 48000.0, true),
                new Employee(6, "Frank", "Sales", 32, 49000.0, false),
                new Employee(7, "Grace", "Engineering", 24, 69000.0, true),
                new Employee(8, "Helen", "HR", 22, 51000.0, false),
                new Employee(9, "Ian", "Sales", 27, 47000.0, true));

        System.out.println("------------------------------------------------------");
        System.out.println("1.Count how many employees fall into each age group");
        Map<String, Long> countAgeRange = employeeList.stream()
                .collect(Collectors.groupingBy(e -> getAgeGroup(e.getAge()).toString(), Collectors.counting()));

        countAgeRange.forEach((k, v) -> { System.out.println(k + ": " + v); });

        System.out.println("------------------------------------------------------");
        System.out.println("2. Find the highest salary in each department among active employees");
        Map<String, Optional<Employee>> highestsal = employeeList.stream()
                .filter(Employee::isActive)
                .collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));

        highestsal.forEach((k, v) -> {
            System.out.println(k + ": " + v.get().getName() + ": " + v.get().getSalary());
        });

        System.out.println("------------------------------------------------------");
        System.out.println("3. List all active employees");
        List<Employee> ActiveEmp = employeeList.stream().filter(Employee::isActive).collect(Collectors.toList());
        for (Employee e : ActiveEmp) {
            System.out.print(e.getName() + ", ");
        }

    }

    private static Object getAgeGroup(int age) {
        if(age>=20 && age<=29){
            return "20-29";
        } else if (age>=30 && age<=39) {
            return "30-39";
        } else {
            return "+40";
        }
    }
}
