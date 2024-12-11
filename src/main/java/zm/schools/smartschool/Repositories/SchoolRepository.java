package zm.schools.smartschool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.School;


public interface SchoolRepository extends JpaRepository<School, Long> {
}
