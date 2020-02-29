package pl.moras.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.moras.models.House;

import java.util.Optional;

public interface HouseRepo extends JpaRepository<House, Long> {
    Optional<House> findByName(String name);
    boolean existsByName(String name);
}
