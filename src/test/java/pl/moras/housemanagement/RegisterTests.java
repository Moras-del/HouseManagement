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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.moras.models.*;
import pl.moras.repos.HouseRepo;
import pl.moras.repos.InmateRepo;
import pl.moras.repos.PlanRepo;
import pl.moras.repos.RoleRepo;
import pl.moras.service.MyService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class RegisterTests {

	@Mock
	private HouseRepo houseRepo;

	@Mock
	private InmateRepo inmateRepo;

	@Mock
	private RoleRepo roleRepo;

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
		when(roleRepo.findByName(anyString())).thenAnswer(answer-> {
			Role role = new Role(answer.getArgument(0));
			return Optional.of(role);
		});
		when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(answer->answer.getArgument(0, String.class).equals(answer.getArgument(1, String.class)));
		when(passwordEncoder.encode(anyString())).thenAnswer(answer->answer.getArgument(0));
	}

	@Test
	void should_add_inmate(){
		InmateDto inmateDto = new InmateDto("newInmate", "password");
		HouseDto houseDto = new HouseDto("house", "password");

		Inmate inmate = myService.addInmate(houseDto, inmateDto);
		assertEquals(inmateDto.getName(), inmate.getName());
	}

	@Test
	void should_add_inmate_fail(){
		InmateDto inmateDto = new InmateDto("newInmate", "password");
		HouseDto houseDto = new HouseDto("house", "badPassword");
		assertThrows(UsernameNotFoundException.class, ()-> myService.addInmate(houseDto, inmateDto));
	}

	@Test
	void should_add_house(){
		HouseDto houseDto = new HouseDto("newHouse", "password");
		InmateDto inmateDto = new InmateDto("newInmate", "password");
		House house = myService.addHouse(houseDto, inmateDto);
		assertEquals(houseDto.getName(), house.getName());
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
