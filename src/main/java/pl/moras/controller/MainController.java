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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private IMainService mainService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showMainPage(Model model, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        model.addAttribute("house", inmate.getHouse());
        return "mainpage";
    }

    @RequestMapping(value = "/main", method = RequestMethod.PUT)
    public ResponseEntity<Map> takeFromBudget(@RequestParam int expenses, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        House house = mainService.takeFromBudget(inmate, expenses);
        Map<String, Object> response = new HashMap<>();
        response.put("inmate", inmate.getName());
        response.put("budget", house.getBudget());
        response.put("expenses", inmate.getExpenses());
        return ResponseEntity.ok(response);
    }

    @RolesAllowed("HOUSE_ADMIN")
    @RequestMapping(value = "/main/budget", method = RequestMethod.PUT)
    public ResponseEntity setBudget(@RequestParam int budget, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        mainService.setBudget(inmate.getHouse(), budget);
        return ResponseEntity.ok().build();
    }

    @RolesAllowed("HOUSE_ADMIN")
    @RequestMapping(value = "/main/reset", method = RequestMethod.POST)
    public String resetExpenses(Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        mainService.resetExpenses(inmate.getHouse());
        return "redirect:/main";
    }

    @RequestMapping(value = "/plans", method = RequestMethod.GET)
    public String showPlansPage(Model model, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        List<Plan> plans = inmate.getHouse().getPlans();
        model.addAttribute("plans", plans);
        model.addAttribute("planDto", new PlanDto());
        return "planspage";
    }

    @RequestMapping(value = "/plans", method = RequestMethod.PUT)
    public ResponseEntity contribPlan(@RequestBody PlanDto planDto, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        int costLeft = mainService.contribPlan(inmate, planDto);
        return ResponseEntity.ok(costLeft);
    }

    @RequestMapping(value = "/plans", method = RequestMethod.POST)
    public String newPlan(@Valid @ModelAttribute PlanDto planDto, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        mainService.addPlan(inmate, planDto);
        return "redirect:/plans";
    }


}
