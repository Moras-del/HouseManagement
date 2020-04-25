package pl.moras.housemanagement.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.moras.housemanagement.models.HouseInmateDto;
import pl.moras.housemanagement.service.IAuthService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/house/register")
public class HouseController {

    private IAuthService authService;


    @GetMapping
    public String showHouseRegister(Model model){
        model.addAttribute("houseInmateDto", new HouseInmateDto());
        return "houseregister";
    }

    @PostMapping
    public String houseRegister(@Valid @ModelAttribute HouseInmateDto houseInmateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "houseregister";
        }
        authService.addHouse(houseInmateDto);
        authService.authenticate(houseInmateDto);
        return "redirect:/main";
    }
}
