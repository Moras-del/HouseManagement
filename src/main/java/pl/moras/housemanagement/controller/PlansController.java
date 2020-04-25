package pl.moras.housemanagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.models.Plan;
import pl.moras.housemanagement.models.PlanDto;
import pl.moras.housemanagement.service.IMainService;
import pl.moras.housemanagement.service.IPlanService;
import pl.moras.housemanagement.service.MainService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/plans")
public class PlansController {

    private IMainService mainService;
    private IPlanService planService;

    @GetMapping
    public String showPlansPage(Model model, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        List<Plan> plans = inmate.getHouse().getPlans();
        model.addAttribute("plans", plans);
        model.addAttribute("planDto", new PlanDto());
        return "planspage";
    }

    @PutMapping
    public ResponseEntity contribPlan(@RequestBody PlanDto planDto, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        int costLeft = planService.contribPlan(inmate, planDto);
        return ResponseEntity.ok(costLeft);
    }

    @PostMapping
    public String newPlan(@Valid @ModelAttribute PlanDto planDto, Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        planService.addPlan(inmate, planDto);
        return "redirect:/plans";
    }
}
