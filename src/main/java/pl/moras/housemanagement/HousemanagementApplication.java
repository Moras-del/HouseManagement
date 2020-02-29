package pl.moras.housemanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.moras.models.Inmate;
import pl.moras.models.Role;
import pl.moras.repos.RoleRepo;

@SpringBootApplication
public class HousemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousemanagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext applicationContext){
		RoleRepo roleRepo = applicationContext.getBean(RoleRepo.class);
		return args->{
			roleRepo.save(new Role("USER"));
			roleRepo.save(new Role("HOUSE_ADMIN"));
		};

	}

}
