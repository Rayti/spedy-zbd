package com.example.spedy.model;

import java.util.UUID;

public class Employee {

    private final UUID employeeId;
    private final UUID professionId;
    private final UUID userId;
    private String firstName;
    private String lastName;
    private int salary;

    public Employee(UUID employeeId, UUID professionId, UUID userId, String firstName, String lastName, int salary) {
        this.employeeId = employeeId;
        this.professionId = professionId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Employee(UUID professionId, UUID userId, String firstName, String lastName, int salary) {
        this(UUID.randomUUID(), professionId, userId, firstName, lastName, salary);
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public UUID getProfessionId() {
        return professionId;
    }

    public UUID getUserId() {
        return userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return employeeId.equals(employee.employeeId);
    }

    @Override
    public int hashCode() {
        return employeeId.hashCode();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", professionId=" + professionId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
