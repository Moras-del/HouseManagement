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


    @PostMapping(value = "/reset")
    public String resetExpenses(Principal principal){
        Inmate inmate = mainService.getInmate(principal);
        mainService.resetExpenses(inmate.getHouse());
        return "redirect:/main";
    }

}
