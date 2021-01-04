package com.example.spedy.api;

import com.example.spedy.model.Employee;
import com.example.spedy.model.Profession;
import com.example.spedy.model.User;
import com.example.spedy.service.EmployeeService;
import com.example.spedy.service.ProfessionService;
import com.example.spedy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ProfessionService professionService;
    private final UserService userService;

    @Autowired
    public EmployeeController(@Qualifier("employeeService") EmployeeService employeeService,
                              @Qualifier("professionService") ProfessionService professionService,
                              @Qualifier("userService") UserService userService) {
        this.employeeService = employeeService;
        this.professionService = professionService;
        this.userService = userService;
    }

    @GetMapping("/employees")
    public String showAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAll();
        model.addAttribute("employees", employees);
        model.addAttribute("firstNamePattern", "");
        model.addAttribute("lastNamePattern", "");
        return "employees/employees";
    }

    @PostMapping("/employees")
    public String deleteEmployee(@RequestParam UUID deleteId, Model model) {
        Employee employee = employeeService.getEmployee(deleteId);
        String message = employeeService.deleteEmployee(employee);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("/employees/search")
    public String showEmployeesWithSearchPattern(@RequestParam String firstNamePattern,
                                                 @RequestParam String lastNamePattern,
                                                 Model model) {
        List<Employee> employees = employeeService.getAll();
        List<Employee> searchedEmployees = employees.stream()
                .filter(emp ->
                        emp.getFirstName().toLowerCase().contains(firstNamePattern.toLowerCase())
                                && emp.getLastName().toLowerCase().contains(lastNamePattern.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("employees", searchedEmployees);
        model.addAttribute("firstNamePattern", firstNamePattern);
        model.addAttribute("lastNamePattern", lastNamePattern);
        return "employees/employees";
    }

    @GetMapping("/employees/specific")
    public String showSpecificEmployee(@RequestParam UUID id,
                                       Model model) {
        Employee employee = employeeService.getEmployee(id);
        String professionTitle = professionService.getProfession(employee.getProfessionId()).getTitle();
        String userName = userService.getUser(employee.getUserId()).getLogin();
        model.addAttribute("employee", employee);
        model.addAttribute("userName", userName);
        model.addAttribute("title", professionTitle);
        model.addAttribute("numberOfComplaints", 90909);
        model.addAttribute("numberOfDeliveries", 90909);
        return "employees/specificEmployee";
    }

    @GetMapping("/employees/create")
    public String createEmployeeForm(Model model) {
        List<Profession> professions = professionService.getProfessions();
        List<User> users = getUsersWithNoEmployeeConnected();
        model.addAttribute("professions", professions);
        model.addAttribute("users", users);
        return "employees/employeeCreation";

    }

    private List<User> getUsersWithNoEmployeeConnected(){
        List<UUID> connectedUserIdList = employeeService.getAll()
                .stream()
                .map(Employee::getUserId)
                .collect(Collectors.toList());
        return userService.getUsers()
                .stream()
                .filter(user -> !connectedUserIdList.contains(user.getUserId()))
                .collect(Collectors.toList());
    }

    @PostMapping("employees/create")
    public String createEmployee(@RequestParam String newFirstName,
                                 @RequestParam String newLastName,
                                 @RequestParam int newSalary,
                                 @RequestParam UUID newProfessionId,
                                 @RequestParam UUID newUserId,
                                 Model model){
        Profession profession = professionService.getProfession(newProfessionId);
        if (newSalary > profession.getMinSalary() && newSalary < profession.getMaxSalary()) {
            Employee employee = new Employee(newProfessionId, newUserId, newFirstName, newLastName, newSalary);
            String message = employeeService.insertEmployee(employee);
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "Could not create employee. Salary does not match profession constraints.");
        }
        return "info";
    }

    @GetMapping("/employees/update")
    public String updateEmployeeForm(@RequestParam UUID id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        List<Profession> professions = professionService.getProfessions();
        List<User> users = getUsersWithNoEmployeeConnected();
        users.add(userService.getUser(employee.getUserId()));
        model.addAttribute("professions", professions);
        model.addAttribute("users", users);
        model.addAttribute("employee", employee);
        return "employees/employeeUpdate";
    }




    @PostMapping("employees/update")
    public String updateEmployee(@RequestParam UUID oldId,
                                 @RequestParam String newFirstName,
                                 @RequestParam String newLastName,
                                 @RequestParam int newSalary,
                                 @RequestParam UUID newProfessionId,
                                 @RequestParam UUID newUserId,
                                 Model model) {
        Profession profession = professionService.getProfession(newProfessionId);
        if (newSalary > profession.getMinSalary() && newSalary < profession.getMaxSalary()) {
            Employee employee = employeeService.getEmployee(oldId);
            employee = new Employee(employee.getEmployeeId(), newProfessionId, newUserId, newFirstName, newLastName, newSalary);
            String message = employeeService.updateEmployee(employee);
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "Could not update employee. Salary does not match profession constraints.");
        }
        return "info";
    }
}
