package com.example.spedy.service;

import com.example.spedy.dao.SimpleDao;
import com.example.spedy.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("companyService")
public class CompanyService {

    public final SimpleDao<Company> dao;

    @Autowired
    public CompanyService(@Qualifier("postgresCompanyDao") SimpleDao<Company> dao) {
        this.dao = dao;
    }

    public Company getCompany(String name) {
        return dao.select(name);
    }

    public Company getCompany(UUID id) {
        return dao.select(id);
    }

    public List<Company> getCompanies() {
        return dao.selectAll();
    }

    public String deleteCompany(Company company) {
        String responseIfTrue = "Company " + company.getName() + " deleted from database.";
        String responseIfFalse = "Could not delete company. Check spelling.";
        return dao.delete(company) ? responseIfTrue : responseIfFalse;
    }

    public String insertCompany(Company company) {
        if (companyExistsAlready(company)) {
            return "Company with this name already exists.";
        }
        String responseIfTrue = "Company " + company.getName() + " added to database.";
        String responseIfFalse = "Could not add new Company. Check spelling.";
        return dao.insert(company) ? responseIfTrue : responseIfFalse;
    }

    public String updateCompany(Company company) {
        if (companyExistsAlready(company)) {
            return "Company with this name already exists.";
        }
        String responseIfTrue = "Company " + company.getName() + " updated.";
        String responseIfFalse = "Could not update. Check spelling.";
        return dao.update(company) ? responseIfTrue : responseIfFalse;
    }

    private boolean companyExistsAlready(Company company){
        return dao.selectAll().stream()
                .anyMatch(comp -> comp.getName().equals(company.getName())
                    && company.getId().compareTo(company.getId()) != 0);
    }
}
