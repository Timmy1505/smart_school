package zm.schools.smartschool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zm.schools.smartschool.Models.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
