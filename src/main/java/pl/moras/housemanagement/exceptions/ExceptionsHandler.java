package pl.moras.housemanagement.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ExceptionsHandler {

    @RequestMapping
    @ExceptionHandler({
            HouseAlreadyExists.class,
            HouseNotFoundException.class,
            InmateAlreadyExists.class,
            WrongHousePasswordException.class})
    public String showException(RuntimeException exception, Model model){
        model.addAttribute("exception", exception.getMessage());
        return "exceptionpage";
    }

}
