package pl.moras.housemanagement.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.service.IMainService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/budget")
public class BudgetController {

    private IMainService mainService;


    @PutMapping(value="/take")
    public ResponseEntity<Map> takeFromBudget(@RequestParam int expenses, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        House house = mainService.takeFromBudget(inmate, expenses);
        Map<String, Object> response = new HashMap<>();
        response.put("inmate", inmate.getName());
        response.put("budget", house.getBudget());
        response.put("expenses", inmate.getExpenses());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/set")
    public ResponseEntity setBudget(@RequestParam int budget, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        mainService.setBudget(inmate.getHouse(), budget);
        return ResponseEntity.ok().build();
    }

}
