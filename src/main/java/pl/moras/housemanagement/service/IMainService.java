package pl.moras.housemanagement.service;

import pl.moras.housemanagement.models.*;

import java.security.Principal;

public interface IMainService {

    void setBudget(House house, int budget);

    House takeFromBudget(Inmate inmate, int expenses);

    Inmate getInmate(Principal principal);

    void resetExpenses(House house);
}
