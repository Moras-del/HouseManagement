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
@RequestMapping("/inmate")
public class InmateController {

    private IAuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model){
        model.addAttribute("houseInmateDto", new HouseInmateDto());
        return "loginpage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute HouseInmateDto houseInmateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "loginpage";
        authService.authenticate(houseInmateDto);
        return "redirect:/main";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerInmate(Model model){
        model.addAttribute("houseInmateDto", new HouseInmateDto());
        return "inmateregister";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String showRegisterInmate(@Valid @ModelAttribute HouseInmateDto houseInmateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "inmateregister";
        authService.addInmate(houseInmateDto);
        authService.authenticate(houseInmateDto);
        return "redirect:/main";
    }

}
