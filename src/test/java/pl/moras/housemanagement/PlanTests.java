package pl.moras.housemanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.models.Plan;
import pl.moras.housemanagement.models.PlanDto;
import pl.moras.housemanagement.repos.PlanRepo;
import pl.moras.housemanagement.service.IPlanService;
import pl.moras.housemanagement.service.PlanService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlanTests {

    @Mock
    private PlanRepo planRepo;
    private IPlanService planService;

    @BeforeEach
    void setup(){
        initMocks(this);
        when(planRepo.save(any(Plan.class))).thenReturn(new Plan());
        when(planRepo.getPlan(anyInt(), anyString())).thenReturn(Optional.of(getPlan()));
        planService = new PlanService(planRepo);
    }


    @Test
    void should_contribute_to_plan(){
        PlanDto planDto = new PlanDto();
        planDto.setContribution(100);
        planDto.setName("name");

        Inmate inmate = getInmate();
        House house = getHouse();
        house.addInmate(inmate);
        int costLeft = planService.contribPlan(inmate, planDto);
        assertEquals(900, costLeft);
    }

    @Test
    void should_add_plan(){
        PlanDto planDto = new PlanDto();
        planDto.setName("name");
        planDto.setCost(1000);
        Inmate inmate = getInmate();
        House house = getHouse();

        house.addInmate(inmate);
        planService.addPlan(inmate, planDto);
        assertFalse(inmate.getPlans().isEmpty());
        assertFalse(house.getPlans().isEmpty());
    }

    private Inmate getInmate(){
        Inmate inmate = new Inmate();
        inmate.setName("inmate");
        inmate.setPassword("password");
        return inmate;
    }

    private House getHouse(){
        House house = new House();
        house.setName("house");
        house.setPassword("password");
        house.setBudget(20);
        return house;
    }

    private Plan getPlan(){
        Plan plan = new Plan();
        plan.setName("name");
        plan.setCost(1000);
        return plan;
    }
}
