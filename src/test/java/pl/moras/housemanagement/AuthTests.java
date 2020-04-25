package pl.moras.housemanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.moras.housemanagement.exceptions.WrongHousePasswordException;
import pl.moras.housemanagement.models.*;
import pl.moras.housemanagement.repos.HouseRepo;
import pl.moras.housemanagement.repos.InmateRepo;
import pl.moras.housemanagement.repos.RoleRepo;
import pl.moras.housemanagement.service.AuthService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AuthTests {

	@Mock
	private HouseRepo houseRepo;

	@Mock
	private InmateRepo inmateRepo;

	@Mock
	private RoleRepo roleRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	private AuthService authService;

	@BeforeEach
	public void setup(){
		initMocks(this);
		when(inmateRepo.existsByNameAndHouse(getInmate().getName(), getHouse())).thenReturn(true);
		when(inmateRepo.save(any(Inmate.class))).thenAnswer(answer->answer.getArgument(0));
		when(houseRepo.findByName(getHouse().getName())).thenReturn(Optional.of(getHouse()));
		when(houseRepo.existsByName(getHouse().getName())).thenReturn(true);
		when(houseRepo.save(any(House.class))).thenAnswer(answer->answer.getArgument(0));
		when(roleRepo.findByName(anyString())).thenAnswer(answer-> Role.of(answer.getArgument(0)));
		when(roleRepo.findAll()).thenAnswer(answer-> Collections.singletonList(Role.of("HOUSE_ADMIN")));
		when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(answer->answer.getArgument(0, String.class).equals(answer.getArgument(1, String.class)));
		when(passwordEncoder.encode(anyString())).thenAnswer(answer->answer.getArgument(0));
		authService = new AuthService(passwordEncoder, houseRepo, inmateRepo, roleRepo);
	}

	@Test
	void should_add_inmate(){
		HouseInmateDto houseInmateDto = new HouseInmateDto();
		houseInmateDto.setHouseName("house");
		houseInmateDto.setHousePassword("password");
		houseInmateDto.setInmateName("newInmate");
		houseInmateDto.setInmatePassword("password");

		Inmate inmate = authService.addInmate(houseInmateDto);
		assertEquals(houseInmateDto.getInmateName(), inmate.getName());
	}

	@Test
	void should_add_inmate_fail(){
		HouseInmateDto houseInmateDto = new HouseInmateDto();
		houseInmateDto.setHouseName("house");
		houseInmateDto.setHousePassword("badPassword");
		houseInmateDto.setInmateName("newInmate");
		houseInmateDto.setInmatePassword("password");

		assertThrows(WrongHousePasswordException.class, ()-> authService.addInmate(houseInmateDto));
	}

	@Test
	void should_add_house(){
		HouseInmateDto houseInmateDto = new HouseInmateDto();
		houseInmateDto.setHouseName("newHouse");
		houseInmateDto.setHousePassword("password");
		houseInmateDto.setInmateName("newInmate");
		houseInmateDto.setInmatePassword("password");

		House house = authService.addHouse(houseInmateDto);
		assertEquals(houseInmateDto.getHouseName(), house.getName());
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
