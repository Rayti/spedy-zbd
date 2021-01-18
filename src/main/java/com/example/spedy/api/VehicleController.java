package com.example.spedy.api;

import com.example.spedy.model.Vehicle;
import com.example.spedy.service.VehicleService;
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
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(@Qualifier("vehicleService") VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("vehicles")
    public String showAllVehicles(Model model) {
        addToModel(model, null, vehicleService.getVehicles());
        return "vehicles/vehicles";
    }

    @PostMapping("vehicles")
    public String deleteVehicle(@RequestParam String deleteName, Model model) {
        Vehicle vehicle = vehicleService.getVehicle(deleteName);
        String message = vehicleService.deleteVehicle(vehicle);
        addToModel(model, message, vehicleService.getVehicles());
        return "vehicles/vehicles";
    }

    @GetMapping("vehicles/update")
    public String updateVehicleForm(@RequestParam String oldName, Model model) {
        Vehicle vehicle = vehicleService.getVehicle(oldName);
        model.addAttribute("vehicle", vehicle);
        return "vehicles/vehiclesUpdate";
    }

    @PostMapping("vehicles/update")
    public String updateVehicle(@RequestParam String oldName,
                                @RequestParam String newName,
                                @RequestParam int newPower,
                                @RequestParam int newProductionYear,
                                Model model) {
        Vehicle vehicle = changeVehicleData(oldName, newName, newPower, newProductionYear);
        String message = vehicleService.updateVehicle(vehicle);
        addToModel(model, message, vehicleService.getVehicles());
        return "vehicles/vehicles";
    }

    @GetMapping("vehicles/create")
    public String createVehicleForm(Model model) {
        return "vehicles/vehiclesCreation";
    }

    @PostMapping("vehicles/create")
    public String createVehicle(@RequestParam String name,
                                @RequestParam int power,
                                @RequestParam int productionYear,
                                Model model) {
        Vehicle vehicle = new Vehicle(name, power, productionYear);
        String message = vehicleService.insertVehicle(vehicle);
        addToModel(model, message, vehicleService.getVehicles());
        return "vehicles/vehicles";
    }

    @NonNull
    private Vehicle changeVehicleData(String oldName, String newName, int newPower, int newProductionYear) {
        Vehicle vehicle = vehicleService.getVehicle(oldName);
        vehicle.setName(newName);
        vehicle.setPower(newPower);
        vehicle.setProductionYear(newProductionYear);
        return vehicle;
    }

    private void addToModel(Model model, String message, List<Vehicle> vehicles) {
        model.addAttribute("message", message);
        model.addAttribute("vehicles", vehicles);
    }
}
