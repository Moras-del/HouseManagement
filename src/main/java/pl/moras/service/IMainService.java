package pl.moras.service;

import org.springframework.http.ResponseEntity;
import pl.moras.models.*;

import java.security.Principal;

public interface IMainService {

    void setBudget(House house, int budget);

    void addPlan(Inmate inmate, PlanDto planDto);

    House takeFromBudget(Inmate inmate, int expenses);

    int contribPlan(Inmate inmate, PlanDto planDto);

    Inmate getInmateFromPrincipal(Principal principal);

    void resetExpenses(House house);
}
