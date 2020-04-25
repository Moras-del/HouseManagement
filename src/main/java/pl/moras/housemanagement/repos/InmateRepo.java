package pl.moras.housemanagement.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.Inmate;

import java.util.Optional;

public interface InmateRepo extends JpaRepository<Inmate, Long> {

    Optional<Inmate> findByName(String name);
    boolean existsByNameAndHouse(String name, House house);

}
