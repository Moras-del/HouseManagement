package pl.moras.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.moras.models.Inmate;
import pl.moras.models.Plan;
import pl.moras.models.PlanDto;
import pl.moras.service.MainService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/plans")
public class PlansController {

    private MainService mainService;

    @GetMapping
    public String showPlansPage(Model model, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        List<Plan> plans = inmate.getHouse().getPlans();
        model.addAttribute("plans", plans);
        model.addAttribute("planDto", new PlanDto());
        return "planspage";
    }

    @PutMapping
    public ResponseEntity contribPlan(@RequestBody PlanDto planDto, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        int costLeft = mainService.contribPlan(inmate, planDto);
        return ResponseEntity.ok(costLeft);
    }

    @PostMapping
    public String newPlan(@Valid @ModelAttribute PlanDto planDto, Principal principal){
        Inmate inmate = mainService.getInmateFromPrincipal(principal);
        mainService.addPlan(inmate, planDto);
        return "redirect:/plans";
    }
}
