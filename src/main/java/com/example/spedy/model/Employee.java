package com.example.spedy.model;

import java.util.UUID;

public class Employee {

    private final UUID employeeId;
    private String firstName;
    private String lastName;
    private int salary;

    public Employee(UUID employeeId, String firstName, String lastName, int salary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Employee(String firstName, String lastName, int salary) {
        this.employeeId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
