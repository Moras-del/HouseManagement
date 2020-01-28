package pl.moras.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.moras.models.*;
import pl.moras.service.IMyService;
import pl.moras.service.MyService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin
public class HouseController {

    private IMyService myService;

    @GetMapping("/inmate")
    public ResponseEntity<Inmate> getInmate(Principal principal){
        return myService.getInmate(principal);
    }

    @PostMapping("/inmate")
    public  ResponseEntity<Inmate> saveInmate(@RequestBody MyDto myDto){
        return myService.addInmate(myDto);
    }

    @PostMapping("/house")
    public ResponseEntity<House> saveHouse(@RequestBody MyDto myDto){
        return myService.addHouse(myDto);
    }

    @RolesAllowed(value = "HOUSEMANAGER")
    @PutMapping("/house/budget")
    public ResponseEntity<House> setBudget(Principal principal, @RequestParam int budget ){
        return myService.setBudget(principal, budget);
    }

    @PutMapping("/house/expense")
    public ResponseEntity<House> setExpenses(Principal principal, @RequestParam int expenses){
        return myService.setExpenses(principal, expenses);
    }

    @PostMapping(value = "/plan", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Plan> addPlan(Principal principal, @RequestBody PlanDto planDto){
        return myService.addPlan(principal, planDto);
    }

    @PutMapping("/plan")
    public ResponseEntity<Plan> contributeToPlan(Principal principal, @RequestParam int money, @RequestParam String planName){
        return myService.contribPlan(principal, money, planName);
    }


}
