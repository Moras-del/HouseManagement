package pl.moras.housemanagement.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.moras.housemanagement.models.House;

import java.util.Optional;

public interface HouseRepo extends JpaRepository<House, Long> {
    Optional<House> findByName(String name);
    boolean existsByName(String name);
}
