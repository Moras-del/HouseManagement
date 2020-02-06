package pl.moras.service;

import org.springframework.http.ResponseEntity;
import pl.moras.models.*;

import java.security.Principal;

public interface IMyService {

    House addHouse(HouseDto houseDto, InmateDto inmateDto);

    Inmate addInmate(HouseDto houseDto, InmateDto inmateDto);

    Inmate getInmate(Principal principal);

    ResponseEntity<House> setBudget(Principal principal, int budget);

    ResponseEntity<Plan> addPlan(Principal principal, PlanDto planDto);

    ResponseEntity<House> setExpenses(Principal principal, int expenses);

    ResponseEntity<Plan> contribPlan(Principal principal, int money, String planName);
}
