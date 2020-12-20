package com.example.spedy.api;


import com.example.spedy.model.Refueling;
import com.example.spedy.model.Vehicle;
import com.example.spedy.service.RefuelingService;
import com.example.spedy.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.*;

@Controller
public class RefuelingController {

    //TODO Searching for specific vehicle
    //TODO add creating refueling option to every vehicle
    private final RefuelingService refuelingService;
    private final VehicleService vehicleService;

    @Autowired
    public RefuelingController(@Qualifier("refuelingService") RefuelingService refuelingService,
                               @Qualifier("vehicleService") VehicleService vehicleService) {
        this.vehicleService = vehicleService;
        this.refuelingService = refuelingService;
    }

    @GetMapping("refuelings")
    public String showAllRefuelings(Model model) {
        List<Vehicle> vehicles = vehicleService.getVehicles();
        model.addAttribute("refuelings", refuelingService);
        model.addAttribute("vehicles", vehicles);
        return "refuelings/refuelings";
    }


    @GetMapping("refuelings/{vehicleName}")
    public String showSpecificRefuelings(@PathVariable String vehicleName, Model model) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicleService.getVehicle(vehicleName));
        model.addAttribute("refuelings", refuelingService);
        model.addAttribute("vehicles", vehicles);
        return "refuelings/refuelings";
    }

    @PostMapping("refuelings")
    public String deleteRefueling(@RequestParam UUID deleteId, Model model) {
        Refueling refueling = refuelingService.getRefueling(deleteId);
        String message = refuelingService.deleteRefueling(refueling);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("refuelings/update")
    public String updateRefuelingForm(@RequestParam UUID oldId, Model model) {
        Refueling refueling = refuelingService.getRefueling(oldId);
        model.addAttribute("refueling", refueling);
        return "refuelings/refuelingsUpdate";
    }

    @PostMapping("refuelings/update")
    public String updateRefueling(@RequestParam UUID oldId,
                                  @RequestParam int newAmount,
                                  @RequestParam double newPricePerLitre,
                                  @RequestParam String newCountry,
                                  @RequestParam String newCity,
                                  @RequestParam Date newRefuelDate,
                                  Model model) {
        Refueling refueling = refuelingService.getRefueling(oldId);
        refueling.setAmount(newAmount);
        refueling.setPricePerLitre(newPricePerLitre);
        refueling.setCountry(newCountry);
        refueling.setCity(newCity);
        refueling.setRefuelDate(newRefuelDate);
        String message = refuelingService.updateRefueling(refueling);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("refuelings/create")
    public String createRefuelingForm(@RequestParam UUID vehicleId, Model model) {
        model.addAttribute("vehicleId", vehicleId);
        return "refuelings/refuelingsCreation";
    }

    @PostMapping("refuelings/create")
    public String createRefueling(@RequestParam UUID vehicleId,
                                  @RequestParam int amount,
                                  @RequestParam double pricePerLitre,
                                  @RequestParam String country,
                                  @RequestParam String city,
                                  @RequestParam Date refuelDate,
                                  Model model) {
        Refueling refueling = new Refueling(vehicleId, amount, pricePerLitre,
                country, city, refuelDate);
        String message = refuelingService.insertRefueling(refueling);
        model.addAttribute("message", message);
        return "info";
    }

}
