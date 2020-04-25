package pl.moras.housemanagement.service;

import lombok.AllArgsConstructor;
import org.hibernate.PropertyNotFoundException;
import org.springframework.stereotype.Service;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.models.Plan;
import pl.moras.housemanagement.models.PlanDto;
import pl.moras.housemanagement.repos.PlanRepo;

@AllArgsConstructor
@Service
public class PlanService implements IPlanService{

    private PlanRepo planRepo;

    @Override
    public void addPlan(Inmate inmate, PlanDto planDto) {
        String planName = planDto.getName();
        int cost = planDto.getCost();
        House house = inmate.getHouse();
        Plan plan = new Plan();

        plan.setName(planName);
        plan.setCost(cost);
        inmate.addPlan(plan);
        house.addPlan(plan);
        planRepo.save(plan);
    }

    @Override
    public int contribPlan(Inmate inmate, PlanDto planDto) {
        String planName = planDto.getName();
        int contribution = planDto.getContribution();
        int houseId = inmate.getHouse().getId();
        Plan plan = planRepo.getPlan(houseId, planName)
                .orElseThrow(()->new PropertyNotFoundException("Not found"));
        int costLeft = plan.cutCost(contribution);
        if (costLeft <= 0) {
            inmate.addExpenses(contribution + costLeft);
            planRepo.delete(plan);
        }
        else {
            inmate.addExpenses(contribution);
            planRepo.save(plan);
        }
        return costLeft;
    }
}
