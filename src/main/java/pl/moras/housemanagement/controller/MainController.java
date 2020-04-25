package pl.moras.housemanagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.moras.housemanagement.models.*;
import pl.moras.housemanagement.service.IMainService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

    private IMainService mainService;

    @GetMapping
    public String showMainPage(Model model, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        model.addAttribute("house", inmate.getHouse());
        return "mainpage";
    }

    @PutMapping
    public ResponseEntity<Map> takeFromBudget(@RequestParam int expenses, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        House house = mainService.takeFromBudget(inmate, expenses);
        Map<String, Object> response = new HashMap<>();
        response.put("inmate", inmate.getName());
        response.put("budget", house.getBudget());
        response.put("expenses", inmate.getExpenses());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/budget")
    public ResponseEntity setBudget(@RequestParam int budget, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        mainService.setBudget(inmate.getHouse(), budget);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reset")
    public String resetExpenses(Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        mainService.resetExpenses(inmate.getHouse());
        return "redirect:/main";
    }




}
