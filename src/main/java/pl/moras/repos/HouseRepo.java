package pl.moras.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.moras.models.House;

public interface HouseRepo extends JpaRepository<House, Long> {
    House findByName(String name);
    boolean existsByName(String name);
}
