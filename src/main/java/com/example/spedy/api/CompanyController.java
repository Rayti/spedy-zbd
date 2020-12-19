package com.example.spedy.api;

import com.example.spedy.model.Company;
import com.example.spedy.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Controller
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(@Qualifier("companyService") CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("companies")
    public String showAllCompanies(Model model) {
        List<Company> companies = companyService.getCompanies();
        model.addAttribute("companies", companies);
        return "companies/companies";
    }

    @PostMapping("companies")
    public String deleteCompany(@RequestParam String deleteName, Model model) {
        Company company = companyService.getCompany(deleteName);
        String message = companyService.deleteCompany(company);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("companies/create")
    public String createCompanyForm(Model model) {
        return "companies/companiesCreation";
    }

    @PostMapping("companies/create")
    public String createCompany(@RequestParam String name,
                                @RequestParam String description,
                                @RequestParam String country,
                                @RequestParam String city,
                                @RequestParam String address,
                                Model model) {
        Company company = new Company(name, description, country, city, address);
        String message = companyService.insertCompany(company);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("companies/update")
    public String updateCompanyForm(@RequestParam String oldName, Model model) {
        Company company = companyService.getCompany(oldName);
        model.addAttribute(company);
        return "companies/companiesUpdate";
    }

    @PostMapping("companies/update")
    public String updateCompany(@RequestParam String oldName,
                                @RequestParam String newName,
                                @RequestParam String newDescription,
                                @RequestParam String newCountry,
                                @RequestParam String newCity,
                                @RequestParam String newAddress,
                                Model model) {
        Company company = companyService.getCompany(oldName);
        changeCompanyData(newName, newDescription, newCountry, newCity, newAddress, company);
        String message = companyService.updateCompany(company);
        model.addAttribute("message", message);
        return "info";
    }

    private void changeCompanyData(String newName, String newDescription, String newCountry, String newCity, String newAddress, Company company) {
        company.setName(newName);
        company.setDescription(newDescription);
        company.setCountry(newCountry);
        company.setCity(newCity);
        company.setAddress(newAddress);
    }
}
