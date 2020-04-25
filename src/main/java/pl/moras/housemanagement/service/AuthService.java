package pl.moras.housemanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.moras.housemanagement.exceptions.HouseAlreadyExists;
import pl.moras.housemanagement.exceptions.HouseNotFoundException;
import pl.moras.housemanagement.exceptions.InmateAlreadyExists;
import pl.moras.housemanagement.exceptions.WrongHousePasswordException;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.HouseInmateDto;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.repos.HouseRepo;
import pl.moras.housemanagement.repos.InmateRepo;
import pl.moras.housemanagement.repos.RoleRepo;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {

    private PasswordEncoder passwordEncoder;
    private HouseRepo houseRepo;
    private InmateRepo inmateRepo;
    private RoleRepo roleRepo;
    @Override
    public House addHouse(HouseInmateDto houseInmateDto) {
        String houseName = houseInmateDto.getHouseName();
        String housePassword = houseInmateDto.getHousePassword();
        String inmateName = houseInmateDto.getInmateName();
        String inmatePassword = houseInmateDto.getInmatePassword();

        if (houseRepo.existsByName(houseName))
            throw new HouseAlreadyExists();

        Inmate inmate = createInmate(inmateName, inmatePassword, InmateType.HOUSE_ADMIN);
        House house = createHouse(houseName, housePassword, inmate);
        return houseRepo.save(house);
    }


    @Override
    public Inmate addInmate(HouseInmateDto houseInmateDto) {
        String houseName = houseInmateDto.getHouseName();
        String housePassword = houseInmateDto.getHousePassword();
        String inmateName = houseInmateDto.getInmateName();
        String inmatePassword = houseInmateDto.getInmatePassword();

        House house = houseRepo.findByName(houseName)
                .orElseThrow(()->new HouseNotFoundException(houseInmateDto.getHouseName()));

        authenticateHouse(housePassword, house);

        if(inmateRepo.existsByNameAndHouse(inmateName, house))
            throw new InmateAlreadyExists(inmateName, house.getName());

        Inmate inmate = createInmate(inmateName, inmatePassword, InmateType.USER);
        house.addInmate(inmate);
        return inmateRepo.save(inmate);
    }

    @Override
    public boolean authenticate(HouseInmateDto houseInmateDto) {
        String houseName = houseInmateDto.getHouseName();
        String housePassword = houseInmateDto.getHousePassword();
        String inmateName = houseInmateDto.getInmateName();
        String inmatePassword = houseInmateDto.getInmatePassword();

        House house = houseRepo.findByName(houseName)
                .orElseThrow(()->new HouseNotFoundException(houseInmateDto.getHouseName()));

        authenticateHouse(housePassword, house);

        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(inmateName, inmatePassword);
        SecurityContextHolder.getContext().setAuthentication(user);
        return true;
    }

    private House createHouse(String name, String password, Inmate inmate){
        House house = new House();
        house.setName(name);
        house.setPassword(passwordEncoder.encode(password));
        house.addInmate(inmate);
        return house;
    }

    private Inmate createInmate(String name, String password, InmateType inmateType){
        Inmate inmate = new Inmate();
        inmate.setName(name);
        inmate.setPassword(passwordEncoder.encode(password));
        inmate.setRoles(inmateType.getRoles(roleRepo));
        return inmate;
    }

    private void authenticateHouse(String password, House house){
        if (!passwordEncoder.matches(password, house.getPassword()))
            throw new WrongHousePasswordException();
    }

}
