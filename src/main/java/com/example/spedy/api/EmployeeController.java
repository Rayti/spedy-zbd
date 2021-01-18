package com.example.spedy.api;

import com.example.spedy.model.*;
import com.example.spedy.service.*;
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
    private final ComplaintService complaintService;
    private final CompanyService companyService;
    private final FunctionsService functionsService;

    @Autowired
    public EmployeeController(@Qualifier("employeeService") EmployeeService employeeService,
                              @Qualifier("professionService") ProfessionService professionService,
                              @Qualifier("userService") UserService userService,
                              @Qualifier("complaintService") ComplaintService complaintService,
                              @Qualifier("companyService") CompanyService companyService,
                              @Qualifier("functionsService") FunctionsService functionsService) {
        this.employeeService = employeeService;
        this.professionService = professionService;
        this.userService = userService;
        this.complaintService = complaintService;
        this.companyService = companyService;
        this.functionsService = functionsService;
    }

    @GetMapping("/employees")
    public String showAllEmployees(Model model) {
        addToModel(model, "", "",
                employeeService.getAll());
        return "employees/employees";
    }

    @PostMapping("/employees")
    public String deleteEmployee(@RequestParam UUID deleteId,
                                 Model model) {
        Employee employee = employeeService.getEmployee(deleteId);
        String message = employeeService.deleteEmployee(employee);
        model.addAttribute("message", message);
        addToModel(model, "", "", employeeService.getAll());
        return "employees/employees";
    }

    @GetMapping("/employees/search")
    public String showEmployeesWithSearchPattern(@RequestParam String firstNamePattern,
                                                 @RequestParam String lastNamePattern,
                                                 Model model) {
        addToModel(model, firstNamePattern, lastNamePattern,
                employeeService.getAllWithPatterns(firstNamePattern, lastNamePattern));
        return "employees/employees";
    }

    @GetMapping("/employees/specific")
    public String showSpecificEmployee(@RequestParam UUID id, Model model) {
        handleSpecificEmployee(id, model);
        return "employees/specificEmployee";
    }

    @GetMapping("/employees/specific/with/complaints")
    public String showSpecificEmployeeWithComplaintsNr(@RequestParam UUID id,
                                                       Model model){
        handleSpecificEmployee(id, model);
        model.addAttribute("numberOfComplaints", functionsService.getNumberOfComplaintsForEmployee(id));
        return "employees/specificEmployee";
    }

    @GetMapping("/employees/specific/with/deliveries")
    public String showSpecificEmployeeWithDeliveriesNr(@RequestParam UUID id,
                                                       Model model){
        handleSpecificEmployee(id, model);
        model.addAttribute("numberOfDeliveries", functionsService.getNumberOfDeliveriesForEmployee(id));
        return "employees/specificEmployee";
    }

    @GetMapping("/employees/specific/with/compAndDeli")
    public String showSpecificEmployeeWithComplaintsAndDeliveriesNr(@RequestParam UUID id,
                                                                    Model model) {
        handleSpecificEmployee(id, model);
        model.addAttribute("numberOfComplaints", functionsService.getNumberOfComplaintsForEmployee(id));
        model.addAttribute("numberOfDeliveries", functionsService.getNumberOfDeliveriesForEmployee(id));
        return "employees/specificEmployee";
    }

    private void handleSpecificEmployee(@RequestParam UUID id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        String professionTitle = professionService.getProfession(employee.getProfessionId()).getTitle();
        String userName = userService.getUser(employee.getUserId()).getLogin();
        List<ComplaintWithCompany> complaintWithCompanyList = getComplaintsWithCompanies(id);
        model.addAttribute("employee", employee);
        model.addAttribute("userName", userName);
        model.addAttribute("title", professionTitle);
        model.addAttribute("complaints", complaintWithCompanyList);
    }

    private List<ComplaintWithCompany> getComplaintsWithCompanies(UUID employeeId) {
        List<Company> companies = companyService.getCompanies();
        List<Complaint> complaints = complaintService.getAll();
        complaints = complaints.stream()
                .filter(comp -> comp.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
        return complaints.stream()
                .map(comp -> new ComplaintWithCompany(
                        comp,
                        companies.stream().filter(
                                company -> company.getId().equals(comp.getCompanyId())).collect(Collectors.toList()).get(0))
                ).collect(Collectors.toList());
    }

    private class ComplaintWithCompany{
        public Complaint complaint;
        public Company company;

        public ComplaintWithCompany(Complaint complaint, Company company) {
            this.complaint = complaint;
            this.company = company;
        }
    }

    @GetMapping("/employees/create")
    public String createEmployeeForm(Model model) {
        List<Profession> professions = professionService.getProfessions();
        List<User> users = getUsersWithNoEmployeeConnected();
        model.addAttribute("professions", professions);
        model.addAttribute("users", users);
        addToModel(model, "", "", null);
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
        addToModel(model, "", "", employeeService.getAll());
        return "employees/employees";
    }

    @GetMapping("/employees/update")
    public String updateEmployeeForm(@RequestParam UUID id,
                                     Model model) {
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
        handleSpecificEmployee(oldId, model);
        return "employees/specificEmployee";
    }

    private void addToModel(Model model, String firstNamePattern, String lastNamePattern, List<Employee> employees) {
        model.addAttribute("employees", employees);
        model.addAttribute("firstNamePattern", firstNamePattern);
        model.addAttribute("lastNamePattern", lastNamePattern);
    }

}
