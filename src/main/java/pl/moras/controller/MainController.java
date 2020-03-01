package pl.moras.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.moras.models.*;
import pl.moras.service.IMainService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

    private IMainService mainService;

    @GetMapping
    public String showMainPage(Model model, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        model.addAttribute("house", inmate.getHouse());
        return "mainpage";
    }

    @PutMapping
    public ResponseEntity<Map> takeFromBudget(@RequestParam int expenses, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        House house = mainService.takeFromBudget(inmate, expenses);
        Map<String, Object> response = new HashMap<>();
        response.put("inmate", inmate.getName());
        response.put("budget", house.getBudget());
        response.put("expenses", inmate.getExpenses());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/budget")
    public ResponseEntity setBudget(@RequestParam int budget, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        mainService.setBudget(inmate.getHouse(), budget);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reset")
    public String resetExpenses(Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        mainService.resetExpenses(inmate.getHouse());
        return "redirect:/main";
    }




}
