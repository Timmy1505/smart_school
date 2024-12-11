package zm.schools.smartschool.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.MasterGrade;




public interface MasterGradeRepository extends JpaRepository<MasterGrade, Long> {

    Optional<MasterGrade> findByName(String name);
    List<MasterGrade> findAllByNameIn(List<String> names);
    boolean existsByName(String gradeName);

}
