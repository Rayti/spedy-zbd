package com.example.spedy.api;


import com.example.spedy.model.Company;
import com.example.spedy.model.Complaint;
import com.example.spedy.model.Employee;
import com.example.spedy.service.CompanyService;
import com.example.spedy.service.ComplaintService;
import com.example.spedy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class ComplaintController {

    private final ComplaintService complaintService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Autowired
    public ComplaintController(@Qualifier("complaintService") ComplaintService complaintService,
                               @Qualifier("employeeService") EmployeeService employeeService,
                               @Qualifier("companyService") CompanyService companyService) {
        this.complaintService = complaintService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping("employees/complaint")
    public String createComplaintForm(@RequestParam UUID employeeId,
                                      Model model) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Company> companies = companyService.getCompanies();
        model.addAttribute("employee", employee);
        model.addAttribute("companies", companies);
        return "complaints/complaintCreation";
    }

    @PostMapping("employees/complaint")
    public String createComplaint(@RequestParam UUID newEmployeeId,
                                  @RequestParam UUID newCompanyId,
                                  @RequestParam String newDescription,
                                  Model model) {
        Complaint complaint = new Complaint(newEmployeeId, newCompanyId, newDescription);
        String message = complaintService.insertComplaint(complaint);
        model.addAttribute("message", message);
        return "info";
    }

    @PostMapping("employees/delete_complaint")
    public String deleteComplaint(@RequestParam UUID id,
                                  Model model) {
        Complaint complaint = complaintService.getComplaint(id);
        String message = complaintService.deleteComplaint(complaint);
        model.addAttribute("message", message);
        return "info";
    }
}
