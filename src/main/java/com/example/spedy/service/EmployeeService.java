package com.example.spedy.service;

import com.example.spedy.dao.EmployeeDao;
import com.example.spedy.model.Employee;
import com.example.spedy.model.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("employeeService")
public class EmployeeService {

    private final EmployeeDao dao;

    @Autowired
    public EmployeeService(@Qualifier("postgresEmployeeDao") EmployeeDao dao) {
        this.dao = dao;
    }

    public Employee getEmployee(UUID id) {
        return dao.select(id);
    }

    public List<Employee> getAll() {
        return dao.selectAll();
    }

    public List<Employee> getAllForProfessionTitle(String title) {
        return dao.selectAllWithProfession(title);
    }

    public List<Employee> getAllForProfession(Profession profession) {
        return getAllForProfessionTitle(profession.getTitle());
    }

    public String deleteEmployee(Employee employee) {
        String responseIfTrue = "Employee " + employee.getFirstName() + " "
                + employee.getLastName() + " deleted from database.";
        String responseIfFalse = "Could not delete employee. Check spelling.";
        return dao.delete(employee) ? responseIfTrue : responseIfFalse;
    }

    public String insertEmployee(Employee employee) {
        String responseIfTrue = "Employee " + employee.getFirstName() + " "
                + employee.getLastName() + " added.";
        String responseIfFalse = "Could not add employee.Check spelling";
        return dao.insert(employee) ? responseIfTrue : responseIfFalse;
    }

    public String updateEmployee(Employee employee){
        String responseIfTrue = "Employee updated.";
        String responseIfFalse = "Could not update. Check spelling.";
        return dao.update(employee) ? responseIfTrue : responseIfFalse;
    }
}
