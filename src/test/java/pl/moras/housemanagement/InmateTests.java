package pl.moras.housemanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.moras.models.House;
import pl.moras.models.Inmate;
import pl.moras.models.MyDto;
import pl.moras.repos.HouseRepo;
import pl.moras.repos.InmateRepo;
import pl.moras.repos.PlanRepo;
import pl.moras.service.MyService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class InmateTests {

	@Mock
	private HouseRepo houseRepo;

	@Mock
	private InmateRepo inmateRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MyService myService;

	@BeforeEach
	private void setup(){
		initMocks(this);
		when(inmateRepo.existsByName(getInmate().getName())).thenReturn(true);
		when(inmateRepo.save(any(Inmate.class))).thenAnswer(answer->answer.getArgument(0));
		when(houseRepo.findByName(getHouse().getName())).thenReturn(getHouse());
		when(houseRepo.existsByName(getHouse().getName())).thenReturn(true);
		when(houseRepo.save(any(House.class))).thenAnswer(answer->answer.getArgument(0));
		when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(answer->answer.getArgument(0, String.class).equals(answer.getArgument(1, String.class)));
		when(passwordEncoder.encode(anyString())).thenAnswer(answer->answer.getArgument(0));
	}

	@Test
	void should_add_inmate(){
		MyDto myDto = new MyDto();
		myDto.setHouseName("house");
		myDto.setHousePassword("password");
		myDto.setInmateName("newInmate");
		myDto.setInmatePassword("password");
		ResponseEntity<Inmate> responseEntity = myService.addInmate(myDto);
		assertTrue(responseEntity.getStatusCodeValue()==200);
	}

	@Test
	void should_add_house(){
		MyDto myDto = new MyDto();
		myDto.setHouseName("newHouse");
		myDto.setHousePassword("password");
		myDto.setInmateName("newInmate");
		myDto.setInmatePassword("password");
		ResponseEntity<House> responseEntity = myService.addHouse(myDto);
		assertEquals(true, responseEntity.getStatusCodeValue()==200);
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
