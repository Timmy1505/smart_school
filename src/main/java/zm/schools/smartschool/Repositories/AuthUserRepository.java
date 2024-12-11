package zm.schools.smartschool.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
     Optional<AuthUser> findByEmployeeNumber(String employeeNumber);
}
