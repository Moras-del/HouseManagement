package pl.moras.service;

import lombok.AllArgsConstructor;
import org.hibernate.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.moras.models.*;
import pl.moras.repos.HouseRepo;
import pl.moras.repos.InmateRepo;
import pl.moras.repos.PlanRepo;
import pl.moras.repos.RoleRepo;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MyService implements IMyService {

    private PasswordEncoder passwordEncoder;
    private HouseRepo houseRepo;
    private InmateRepo inmateRepo;
    private PlanRepo planRepo;
    private RoleRepo roleRepo;

    @Override
    public House addHouse(HouseInmateDto houseInmateDto) {
        House house = new House();
        Inmate inmate = new Inmate();

        house.setName(houseInmateDto.getHouseName());
        house.setPassword(passwordEncoder.encode(houseInmateDto.getHousePassword()));
        house = houseRepo.save(house);

        inmate.setName(houseInmateDto.getInmateName());
        inmate.setPassword(passwordEncoder.encode(houseInmateDto.getInmatePassword()));
        Role userRole = roleRepo.findByName("USER").orElse(new Role("USER"));
        Role managerRole = roleRepo.findByName("HOUSEMANAGER").orElse(new Role("HOUSEMANAGER"));
        inmate.setRoles(Arrays.asList(userRole, managerRole));
        house.addInmates(inmate);
        inmateRepo.save(inmate);
        return house;
    }

    @Override
    public Inmate addInmate(HouseInmateDto houseInmateDto) {
        House house = houseRepo.findByName(houseInmateDto.getHouseName());
        if (!passwordEncoder.matches(houseInmateDto.getHousePassword(), house.getPassword()))
            throw new UsernameNotFoundException("bad password");
        Inmate inmate = new Inmate();
        inmate.setName(houseInmateDto.getInmateName());
        inmate.setPassword(passwordEncoder.encode(houseInmateDto.getInmatePassword()));
        inmate.setRoles(Collections.singletonList(roleRepo.findByName("USER").orElse(new Role("USER"))));
        house.addInmates(inmate);
        return inmateRepo.save(inmate);
    }

    @Override
    public Inmate getInmate(Principal principal) {
        return inmateRepo.findByName(principal.getName()).orElseThrow(()-> new UsernameNotFoundException("Not found"));
    }

    @Override
    public ResponseEntity<House> setBudget(Principal principal, int budget) {
        if (inmateRepo.findByName(principal.getName()).isPresent()){
            Inmate inmate = inmateRepo.findByName(principal.getName()).get();
            House house = inmate.getHouse();
            house.setBudget(budget);
            return new ResponseEntity<>(houseRepo.save(house), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<Plan> addPlan(Principal principal, PlanDto planDto) {
        if (inmateRepo.findByName(principal.getName()).isPresent()){
            Inmate inmate = inmateRepo.findByName(principal.getName()).get();
            House house = inmate.getHouse();
            Plan plan = new Plan();
            plan.setName(planDto.getName());
            plan.setCost(planDto.getCost());
            inmate.addPlans(plan);
            house.addPlans(plan);
            return new ResponseEntity<>(planRepo.save(plan), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<House> setExpenses(Principal principal, int expenses) {
        if (inmateRepo.findByName(principal.getName()).isPresent()){
            Inmate inmate = inmateRepo.findByName(principal.getName()).get();
            House house = inmate.getHouse();
            inmate.setExpenses(inmate.getExpenses()+expenses);
            house.setBudget(house.getBudget()-expenses);
            inmateRepo.save(inmate);
            return new ResponseEntity<>(houseRepo.save(house), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<Plan> contribPlan(Principal principal, int money, String planName) {
        Inmate inmate = inmateRepo.findByName(principal.getName()).orElseThrow(()->new PropertyNotFoundException("Not found"));
        int houseId = inmate.getHouse().getId();
        Plan plan = planRepo.getPlan(houseId, planName).orElseThrow(()->new PropertyNotFoundException("Not found"));

        if (plan.getCost() > 0) {
            inmate.setExpenses(inmate.getExpenses()+money);
            plan.setCost(plan.getCost() - money);
        }

        inmateRepo.save(inmate);
        return new ResponseEntity<>(planRepo.save(plan), HttpStatus.OK);
    }

}
