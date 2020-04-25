package pl.moras.housemanagement.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.moras.housemanagement.models.Plan;

import java.util.Optional;

public interface PlanRepo extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT p FROM Plan p WHERE house_id=?1 AND name=?2")
    Optional<Plan> getPlan(int houseId, String planName);

}
