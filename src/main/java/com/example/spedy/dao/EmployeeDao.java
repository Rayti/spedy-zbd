package com.example.spedy.dao;

import com.example.spedy.model.Employee;
import com.example.spedy.model.Profession;

import java.util.List;
import java.util.UUID;

public interface EmployeeDao {

    boolean insert(Employee employee);


    boolean update(Employee employee);

    boolean delete(Employee employee);

    List<Employee> selectAll();

    List<Employee> selectAllWithProfession(String professionTitle);

    Employee select(UUID id);

    Employee selectWithUserId(UUID userId);

    Employee selectWithUserName(String username);

}
