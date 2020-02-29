package pl.moras.service;

import lombok.AllArgsConstructor;
import org.hibernate.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.moras.exceptions.HouseAlreadyExists;
import pl.moras.exceptions.HouseNotFoundException;
import pl.moras.exceptions.InmateAlreadyExists;
import pl.moras.exceptions.WrongHousePasswordException;
import pl.moras.models.*;
import pl.moras.repos.HouseRepo;
import pl.moras.repos.InmateRepo;
import pl.moras.repos.PlanRepo;
import pl.moras.repos.RoleRepo;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MainService implements IMainService {

    private HouseRepo houseRepo;
    private InmateRepo inmateRepo;
    private PlanRepo planRepo;

    @Override
    public void setBudget(House house, int budget) {
        house.setBudget(budget);
        houseRepo.save(house);
    }

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
    public House takeFromBudget(Inmate inmate, int expenses) {
        House house = inmate.getHouse();
        inmate.addExpenses(expenses);
        house.cutBudget(expenses);
        inmateRepo.save(inmate);
        return house;
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

    @Override
    public Inmate getInmateFromPrincipal(Principal principal) {
        String username = principal.getName();
        return inmateRepo.findByName(username)
                            .orElseThrow(()->new UsernameNotFoundException("Username not found"));
    }

    @Override
    public void resetExpenses(House house) {
        List<Inmate> list = house.getInmates();
        list.forEach(inmate -> inmate.setExpenses(0));
        houseRepo.save(house);
    }
}
