package pl.moras.housemanagement;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("/house")
public class HouseController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String houseregister(Model model){
        return "houseregister";
    }
}
