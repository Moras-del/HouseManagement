package pl.moras.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.moras.models.HouseDto;

@Controller
@RequestMapping("/house")
public class HouseController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String houseregister(Model model){
        model.addAttribute("house", new HouseDto());
        return "houseregister";
    }
}
