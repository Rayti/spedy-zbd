package com.example.spedy.api;

import com.example.spedy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(@Qualifier("employeeService") EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
