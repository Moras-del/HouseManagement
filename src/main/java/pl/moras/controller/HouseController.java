package pl.moras.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.moras.models.HouseInmateDto;
import pl.moras.service.AuthService;
import pl.moras.service.IAuthService;
import pl.moras.service.MainService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/house")
public class HouseController {

    private IAuthService authService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showHouseRegister(Model model){
        model.addAttribute("houseInmateDto", new HouseInmateDto());
        return "houseregister";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String houseRegister(@Valid @ModelAttribute HouseInmateDto houseInmateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "houseregister";
        }
        authService.addHouse(houseInmateDto);
        authService.authenticate(houseInmateDto);
        return "redirect:/main";
    }
}
