package pl.moras.housemanagement.service;

import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.models.PlanDto;

public interface IPlanService {

    void addPlan(Inmate inmate, PlanDto planDto);

    int contribPlan(Inmate inmate, PlanDto planDto);
}
