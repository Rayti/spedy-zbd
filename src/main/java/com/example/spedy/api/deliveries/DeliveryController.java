package com.example.spedy.api.deliveries;

import com.example.spedy.dao.deliveryDao.DeliveryOrderOptions;
import com.example.spedy.model.Cargo;
import com.example.spedy.model.Company;
import com.example.spedy.model.Employee;
import com.example.spedy.model.Vehicle;
import com.example.spedy.model.deliveries.Delivery;
import com.example.spedy.model.deliveries.DeliveryBuilder;
import com.example.spedy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final VehicleService vehicleService;
    private final CargoService cargoService;

    @Autowired
    public DeliveryController(@Qualifier("deliveryService") DeliveryService deliveryService,
                              @Qualifier("employeeService") EmployeeService employeeService,
                              @Qualifier("companyService") CompanyService companyService,
                              @Qualifier("vehicleService") VehicleService vehicleService,
                              @Qualifier("cargoService") CargoService cargoService) {
        this.deliveryService = deliveryService;
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.vehicleService = vehicleService;
        this.cargoService = cargoService;
    }

    @GetMapping("deliveries")
    public String showAll(Model model) {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("oldSpecialOption", "none");
        model.addAttribute("oldOrderBy", 1);
        return "deliveries/deliveries";
    }

    @PostMapping("deliveries")
    public String deleteDelivery(@RequestParam UUID deliveryId, Model model) {
        Delivery delivery = deliveryService.getDelivery(deliveryId);
        String message = deliveryService.deleteDelivery(delivery);
        model.addAttribute("message", message);
        model.addAttribute("deliveries", deliveryService.getAllDeliveries());
        model.addAttribute("oldSpecialOption", "none");
        model.addAttribute("oldOrderBy", 1);
        return "deliveries/deliveries";
    }

    @GetMapping("deliveries/search")
    public String showAllFilteredDeliveries(@RequestParam String searchPattern,
                                            @RequestParam int orderBy,
                                            @RequestParam String specialOption,
                                            @RequestParam String startedBeforeDate,
                                            @RequestParam String startedAfterDate,
                                            @RequestParam String startedBetweenBeforeDate,
                                            @RequestParam String startedBetweenAfterDate,
                                            Model model){
        List<Delivery> deliveries = handleSearchWithFilters(searchPattern,
                orderBy, specialOption, startedAfterDate, startedBeforeDate,
                startedBetweenAfterDate, startedBetweenBeforeDate);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("oldSearchPattern", searchPattern);
        model.addAttribute("oldOrderBy", orderBy);
        model.addAttribute("oldSpecialOption", specialOption);
        model.addAttribute("oldStartedBeforeDate", startedBeforeDate);
        model.addAttribute("oldStartedAfterDate", startedAfterDate);
        model.addAttribute("oldStartedBetweenBeforeDate", startedBetweenBeforeDate);
        model.addAttribute("oldStartedBetweenAfterDate", startedBetweenAfterDate);
        return "deliveries/deliveries";
    }

    @GetMapping("deliveries/create")
    public String createDeliveryForm(Model model){
        List<Employee> employees = employeeService.getAll();
        List<Company> companies = companyService.getCompanies();
        List<Vehicle> vehicles = vehicleService.getVehicles();
        List<Cargo> cargos = cargoService.getCargos();

        model.addAttribute("employees", employees);
        model.addAttribute("companies", companies);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("cargos", cargos);
        return "deliveries/deliveryCreation";
    }

    @PostMapping("deliveries/create")
    public String createDelivery(HttpServletRequest request, Model model) {
        if(!checkDataInput(request, model)){
            return "info";
        }
        Delivery delivery = DeliveryBuilder.begin()
                .withObligatoryIds(
                        UUID.randomUUID(),
                        UUID.fromString(request.getParameter("newEmployeeId")),
                        UUID.fromString(request.getParameter("newFromCompanyId")),
                        UUID.fromString(request.getParameter("newToCompanyId")),
                        UUID.fromString(request.getParameter("newVehicleId")),
                        UUID.fromString(request.getParameter("newCargoId")))
                .withObligatoryWeight(
                        Integer.parseInt(request.getParameter("newWeight")))
                .withObligatoryDates(
                        Date.valueOf(request.getParameter("newStartDate")),
                        Date.valueOf(request.getParameter("newFinishDate")),
                        request.getParameter("newIsFinished"))
                .get();
        if (delivery.getIsFinished() == null) throw new NullPointerException("No isFinished value");
        String message = deliveryService.insertDelivery(delivery);
        model.addAttribute("message", message);
        model.addAttribute("deliveries", deliveryService.getAllDeliveries());
        model.addAttribute("oldSpecialOption", "none");
        model.addAttribute("oldOrderBy", 1);
        return "deliveries/deliveries";
    }

    private boolean checkDataInput(HttpServletRequest request, Model model) {
        if(request.getParameter("newEmployeeId") == null ||
                request.getParameter("newFromCompanyId") == null ||
                request.getParameter("newToCompanyId") == null ||
                request.getParameter("newVehicleId") == null ||
                request.getParameter("newCargoId") == null ||
                request.getParameter("newWeight").isEmpty() ||
                request.getParameter("newStartDate") == null ||
                request.getParameter("newFinishDate") == null ||
                request.getParameter("newIsFinished") == null ){
            model.addAttribute("message", "Not enough data to create delivery!");
            return false;
        }
        return true;
    }

    private List<Delivery> handleSearchWithFilters(String pattern, int orderBy,
                                                       String specialOption,
                                                       String startedAfter,
                                                       String startedBefore,
                                                       String startedBetweenAfter,
                                                       String startedBetweenBefore){
        switch (specialOption){
            case "finished":
                return deliveryService
                        .withPattern(pattern)
                        .withOrder(DeliveryOrderOptions.getOptionByNumber(orderBy))
                        .getAllFinished();
            case "unfinished":
                return deliveryService
                        .withPattern(pattern)
                        .withOrder(DeliveryOrderOptions.getOptionByNumber(orderBy))
                        .getAllUnfinished();
            case "startedBefore":
                if(!startedBefore.isEmpty()){
                    return deliveryService
                            .withPattern(pattern)
                            .withOrder(DeliveryOrderOptions.getOptionByNumber(orderBy))
                            .getAllStartedBeforeDate(Date.valueOf(startedBefore));
                }else return Collections.emptyList();
            case "startedAfter":
                if(!startedAfter.isEmpty()){
                    return deliveryService
                            .withPattern(pattern)
                            .withOrder(DeliveryOrderOptions.getOptionByNumber(orderBy))
                            .getAllStartedAfterDate(Date.valueOf(startedAfter));
                }else return Collections.emptyList();
            case "startedBetween":
                if(!startedBetweenBefore.isEmpty() && !startedBetweenAfter.isEmpty()){
                    return deliveryService
                            .withPattern(pattern)
                            .withOrder(DeliveryOrderOptions.getOptionByNumber(orderBy))
                            .getAllStartedBetweenDates(Date.valueOf(startedBetweenBefore),
                                    Date.valueOf(startedBetweenAfter));
                }else return Collections.emptyList();
            default:
                return deliveryService
                        .withPattern(pattern)
                        .withOrder(DeliveryOrderOptions.getOptionByNumber(orderBy))
                        .getAllDeliveries();
        }
    }

}
