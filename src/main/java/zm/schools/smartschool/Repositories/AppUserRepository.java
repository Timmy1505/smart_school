package zm.schools.smartschool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.AppUser;



public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
}
