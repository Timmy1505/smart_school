package zm.schools.smartschool.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.Guardian;


public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    Optional<Guardian> findByName(String name);
}
