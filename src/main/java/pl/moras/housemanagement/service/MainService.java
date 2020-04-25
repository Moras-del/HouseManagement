package pl.moras.housemanagement.service;

import lombok.AllArgsConstructor;
import org.hibernate.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.moras.housemanagement.models.*;
import pl.moras.housemanagement.repos.HouseRepo;
import pl.moras.housemanagement.repos.InmateRepo;
import pl.moras.housemanagement.repos.PlanRepo;

import java.security.Principal;
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
    public House takeFromBudget(Inmate inmate, int expenses) {
        House house = inmate.getHouse();
        inmate.addExpenses(expenses);
        house.cutBudget(expenses);
        inmateRepo.save(inmate);
        return house;
    }


    @Override
    public Inmate getInmate(Principal principal) {
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
