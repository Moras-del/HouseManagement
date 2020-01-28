package pl.moras.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.moras.models.Inmate;

import java.util.Optional;

public interface InmateRepo extends JpaRepository<Inmate, Long> {

    Optional<Inmate> findByName(String name);
    boolean existsByName(String name);
}
