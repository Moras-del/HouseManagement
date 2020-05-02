package pl.moras.housemanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.repos.HouseRepo;
import pl.moras.housemanagement.repos.InmateRepo;
import pl.moras.housemanagement.repos.PlanRepo;
import pl.moras.housemanagement.service.IMainService;
import pl.moras.housemanagement.service.MainService;

import java.security.Principal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MainTests {

    @Mock
    private HouseRepo houseRepo;

    @Mock
    private InmateRepo inmateRepo;

    @Mock
    private PlanRepo planRepo;

    private IMainService mainService;

    @BeforeEach
    void setup(){
        initMocks(this);
        when(houseRepo.save(any(House.class))).thenReturn(new House());
        when(inmateRepo.findByName("inmate")).thenReturn(Optional.of(getInmate()));
        mainService = new MainService(houseRepo, inmateRepo, planRepo);
    }

    @Test
    void should_set_budget(){
        House house = getHouse();
        mainService.setBudget(house, 2000);
        assertEquals(2000, house.getBudget());
    }

    @Test
    void should_take_money_from_budget(){
        House house = getHouse();
        Inmate inmate = getInmate();
        house.addInmate(inmate);
        House resultHouse = mainService.takeFromBudget(inmate, 10);
        assertEquals(10, resultHouse.getBudget());
    }

    @Test
    void should_reset_expenses_of_all_inmates(){
        House house = getHouse();
        for (int i = 1; i <=3; i++){
            Inmate inmate = new Inmate();
            inmate.setExpenses(i*100);
            house.addInmate(inmate);
        }
        mainService.resetExpenses(house);
        for (Inmate inmate: house.getInmates())
            assertEquals(0, inmate.getExpenses());
    }

    @Test
    void should_return_inmate_from_principal(){
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("inmate");
        Inmate inmate = mainService.getInmate(principal);
        assertEquals("inmate", inmate.getName());
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
}
