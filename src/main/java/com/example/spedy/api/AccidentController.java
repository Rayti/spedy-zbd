package com.example.spedy.api;

import com.example.spedy.model.Accident;
import com.example.spedy.model.deliveries.Delivery;
import com.example.spedy.service.AccidentService;
import com.example.spedy.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class AccidentController {

    private final AccidentService accidentService;
    private final DeliveryService deliveryService;

    @Autowired
    public AccidentController(@Qualifier("accidentService") AccidentService accidentService,
                              @Qualifier("deliveryService") DeliveryService deliveryService) {
        this.accidentService = accidentService;
        this.deliveryService = deliveryService;
    }

    @GetMapping("accidents")
    public String showAccidentsWithDeliveryId(@RequestParam UUID deliveryId,
                                              Model model) {
        List<Accident> accidents = accidentService.getAllWithDeliveryId(deliveryId);
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        model.addAttribute("accidents", accidents);
        model.addAttribute("delivery", delivery);
        return "accidents/accidents";
    }

    @PostMapping("accidents")
    public String deleteAccident(@RequestParam UUID accidentId,
                                 Model model) {
        Accident accident = accidentService.getAccident(accidentId);
        String message = accidentService.deleteAccident(accident);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("accidents/create")
    public String createAccidentForm(@RequestParam UUID deliveryId,
                                 Model model) {
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        model.addAttribute("delivery", delivery);
        return "accidents/accidentCreation";
    }

    @PostMapping("accidents/create")
    public String createAccident(@RequestParam UUID deliveryId,
                                 @RequestParam String eventDate,
                                 @RequestParam String description,
                                 Model model) {
        if(eventDate.isEmpty()){
           model.addAttribute("message", "Date field is empty!");
           return "info";
        }
        Accident accident = new Accident(UUID.randomUUID(),
                deliveryId,
                Date.valueOf(eventDate),
                description);
        String message = accidentService.insertAccident(accident);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("accidents/update")
    public String updateAccidentForm(@RequestParam UUID accidentId,
                                     @RequestParam UUID deliveryId,
                                     Model model) {
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        Accident accident = accidentService.getAccident(accidentId);
        model.addAttribute("accident", accident);
        model.addAttribute("delivery", delivery);
        return "accidents/accidentUpdate";
    }

    @PostMapping("accidents/update")
    public String updateAccident(@RequestParam UUID accidentId,
                                 @RequestParam String eventDate,
                                 @RequestParam String description,
                                 Model model) {
        if (eventDate.isEmpty()) {
            model.addAttribute("message", "Date field is empty!");
            return"info";
        }
        Accident accident = accidentService.getAccident(accidentId);
        accident.setEventDate(Date.valueOf(eventDate));
        accident.setDescription(description);
        String message = accidentService.updateAccident(accident);
        model.addAttribute("message", message);
        return "info";
    }

}
