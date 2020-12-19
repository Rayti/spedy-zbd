package com.example.spedy.api;

import com.example.spedy.model.Cargo;
import com.example.spedy.service.CargoService;
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
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(@Qualifier("cargoService") CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("cargos")
    public String showAllCargos(Model model) {
        List<Cargo> cargoList = cargoService.getCargos();
        model.addAttribute("cargos", cargoList);
        return "cargos/cargos";
    }

    @PostMapping("cargos")
    public String deleteCargo(@RequestParam String deleteName, Model model) {
        Cargo cargo = cargoService.getCargo(deleteName);
        String message = cargoService.deleteCargo(cargo);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("cargos/create")
    public String createCargoForm(Model model) {
        return "cargos/cargosCreation";
    }

    @PostMapping("cargos/create")
    public String createCargo(@RequestParam String name,
                              @RequestParam String description,
                              Model model) {
        Cargo cargo = new Cargo(name, description);
        String message = cargoService.insertCargo(cargo);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("cargos/update")
    public String updateCargoForm(@RequestParam String name, Model model) {
        Cargo cargo = cargoService.getCargo(name);
        model.addAttribute("cargo", cargo);
        return "cargos/cargosUpdate";
    }

    @PostMapping("cargos/update")
    public String updateCargo(@RequestParam String oldName,
                              @RequestParam String newName,
                              @RequestParam String newDescription,
                              Model model) {
        Cargo cargo = changeCargoNameAndDescription(oldName, newName, newDescription);
        String message = cargoService.updateCargo(cargo);
        model.addAttribute("message", message);
        return "info";
    }

    @NonNull
    private Cargo changeCargoNameAndDescription(String oldName, String newName, String newDescription) {
        Cargo cargo = cargoService.getCargo(oldName);
        cargo.setName(newName);
        cargo.setDescription(newDescription);
        return cargo;
    }

}
