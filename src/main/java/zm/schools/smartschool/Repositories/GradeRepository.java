package zm.schools.smartschool.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.MasterGrade;
import zm.schools.smartschool.Models.School;





public interface GradeRepository extends JpaRepository<Grade, Long> {

    boolean existsByMasterGradeAndSchool(MasterGrade masterGrade, School school);

    Optional<Grade> findByIdAndSchoolId(Long gradeId, Long id);

    List<Grade> findBySchoolId(Long schoolId);
}