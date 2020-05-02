package pl.moras.housemanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.moras.housemanagement.models.Role;
import pl.moras.housemanagement.repos.RoleRepo;

@SpringBootApplication
public class HousemanagementApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HousemanagementApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(HousemanagementApplication.class);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext applicationContext){
		RoleRepo roleRepo = applicationContext.getBean(RoleRepo.class);
		return args->{
			roleRepo.save(Role.of("USER"));
			roleRepo.save(Role.of("HOUSE_ADMIN"));
		};

	}

}
