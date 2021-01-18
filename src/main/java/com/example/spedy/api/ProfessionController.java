package com.example.spedy.api;


import com.example.spedy.model.Profession;
import com.example.spedy.service.FunctionsService;
import com.example.spedy.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ProfessionController {

    private final ProfessionService professionService;
    private final FunctionsService functionsService;

    @Autowired
    public ProfessionController(@Qualifier("professionService") ProfessionService professionService,
                                @Qualifier("functionsService") FunctionsService functionsService) {
        this.professionService = professionService;
        this.functionsService = functionsService;
    }

    @GetMapping("professions")
    public String showAllProfessions(Model model) {
        List<Profession> professionList = professionService.getProfessions();
        model.addAttribute("professions", professionList);
        return "professions/professions";
    }

    @PostMapping("professions")
    public String deleteProfession(@RequestParam String deleteTitle, Model model) {
        Profession profession = professionService.getProfession(deleteTitle);
        String message = professionService.deleteProfession(profession);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("professions/increase")
    public String increasePayRangeForAllProfessions(Model model){
        functionsService.increaseAllProfessionsPayRange(0.03f);
        List<Profession> professionList = professionService.getProfessions();
        model.addAttribute("professions", professionList);
        return "professions/professions";
    }

    @GetMapping("professions/update")
    public String updateProfessionForm(@RequestParam String title, Model model) {
        Profession profession = professionService.getProfession(title);
        model.addAttribute("profession", profession);
        return "professions/professionsUpdate";
    }

    @PostMapping("professions/update")
    public String updateProfession(@RequestParam String oldTitle,
                                   @RequestParam String newTitle,
                                   @RequestParam int newMinSalary,
                                   @RequestParam int newMaxSalary,
                                   Model model) {
        Profession profession = changeProfessionTitleAndSalaries(oldTitle, newTitle, newMinSalary, newMaxSalary);
        String message = professionService.updateProfession(profession);
        model.addAttribute("message", message);
        return "info";
    }

    @NonNull
    private Profession changeProfessionTitleAndSalaries(String oldTitle, String newTitle, int newMinSalary, int newMaxSalary) {
        Profession profession = professionService.getProfession(oldTitle);
        profession.setTitle(newTitle);
        profession.setMinSalary(newMinSalary);
        profession.setMaxSalary(newMaxSalary);
        return profession;
    }


    @GetMapping("professions/create")
    public String createProfessionForm(Model model) {
        return "professions/professionsCreation";
    }

    @PostMapping("professions/create")
    public String createProfession(@RequestParam String title,
                                   @RequestParam int minSalary,
                                   @RequestParam int maxSalary,
                                   Model model) {
        Profession profession = new Profession(minSalary, maxSalary, title);
        String message = professionService.insertProfession(profession);
        model.addAttribute("message", message);
        return "info";
    }

}
