package pl.moras.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.moras.models.HouseInmateDto;
import pl.moras.service.MyService;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/house")
public class HouseController {

    private MyService myService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showHouseRegister(Model model){
        model.addAttribute("houseInmateDto", new HouseInmateDto());
        return "houseregister";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String houseRegister(@ModelAttribute HouseInmateDto houseInmateDto){
        myService.addHouse(houseInmateDto);
        return "houseregister";
    }
}
