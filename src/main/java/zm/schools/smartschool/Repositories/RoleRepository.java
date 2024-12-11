package zm.schools.smartschool.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

   Role  findByName(String name);
    
}
